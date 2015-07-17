package com.panpages.bow.controller;

import com.panpages.bow.configuration.ConfigConstant;
import com.panpages.bow.model.Customer;
import com.panpages.bow.model.CustomerSurveys;
import com.panpages.bow.model.Survey;
import com.panpages.bow.model.SurveyForm;
import com.panpages.bow.model.SurveyTemplate;
import com.panpages.bow.service.mail.MailService;
import com.panpages.bow.service.report.ReportService;
import com.panpages.bow.service.report.ReportType;
import com.panpages.bow.service.survey.CustomerService;
import com.panpages.bow.service.survey.SurveyCalculate;
import com.panpages.bow.service.survey.SurveyCalculateFactory;
import com.panpages.bow.service.survey.SurveyFieldName;
import com.panpages.bow.service.survey.SurveyService;
import com.panpages.bow.service.survey.SurveyStatus;
import com.panpages.bow.service.utils.OfficeFileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class SurveyController {

	@Autowired ApplicationContext ctx;
	
	@Autowired
	CustomerService customerSvc;
	
	@Autowired
	SurveyService surveySvc;
	
	@Autowired
	ReportService reportSvc;
	
	@Autowired
	MailService emailSvc;
	
	@Autowired
	SurveyCalculateFactory calculationFactory;
	
	private static final Logger logger = Logger.getLogger(SurveyController.class);

	@RequestMapping(value = { "/", "/index.html" }, method = RequestMethod.GET)
	public String home(ModelMap model) {
		//return "index";
		return "allsurveys";
	}
	
	@RequestMapping(value = { "/templates.html" }, method = RequestMethod.GET)
	public String listTemplates(ModelMap model) {
		List<SurveyTemplate> surveyTemplates = surveySvc.findAllSurveyTemplates();
		model.addAttribute("templates", surveyTemplates);
		return "templates";
	}
	
	@RequestMapping(value = { "/customers.html" }, method = RequestMethod.GET)
	public String listCustomer(ModelMap model) {
		List<Customer> customers = customerSvc.findAllCustomer();
		model.addAttribute("customers", customers);
		return "customers";
	}
	
	@RequestMapping(value = { "/customer_{customerId}.html" }, method = RequestMethod.GET)
	public String customerProposal(ModelMap model, @PathVariable int customerId) {
		List<CustomerSurveys> proposals = customerSvc.findSurveysByCustomerId(customerId);
                Customer customer = customerSvc.findCustomerById(customerId);
		model.addAttribute("proposals", proposals);
                model.addAttribute("alias", customer.getAlias());
		return "customer_survey";
	}
	
        @RequestMapping(value = {"/cussurvey_{customerSurveyId}.html"}, method = RequestMethod.GET)
         public String showSurvey(ModelMap model, @PathVariable int customerSurveyId) {
             CustomerSurveys survey = customerSvc.findSurveyByCusSurId(customerSurveyId);

             if (survey.getSurveyList() != null) {
                 List<String> lstSurvey = Arrays.asList(survey.getSurveyList().trim().split(":"));

                 model.addAttribute("lstSurvey", lstSurvey);

             }
             model.addAttribute("survey", survey);
             return "templates/template_1/survey_customer_1";
         }
         
         @RequestMapping(value = {"/cussurvey_{customerSurveyId}.html"}, method = RequestMethod.POST)
	public String saveCusSurvey(@PathVariable int customerSurveyId, 
							 @ModelAttribute @Valid SurveyForm form,
							 @RequestParam(value = "preview", required = false) String preview,
							 @RequestParam(value = "submit", required = false) String submit,
							 BindingResult result, 
							 ModelMap model) {
        	 customerSurveyId = 1;
		int surveyId = surveySvc.saveSurveyForm(customerSurveyId, form);		
		
		// Redirect to error page with surveyId -1
		if (surveyId == -1) {
			return "redirect:/error.html";
		}
		
		// Export survey
		Survey survey = surveySvc.findSurveyWithId(surveyId);
		SurveyCalculate surveyCalc = calculationFactory.createSurveyCalculator(customerSurveyId);
		surveyCalc.calculateFields(survey, surveySvc, ctx);
		// Refresh survey
		survey = surveySvc.findSurveyWithId(surveyId);
		// Set fulfilled date
		survey.setFulfilledDate();
		String reportName = reportSvc.exportReport(survey, ReportType.PDF.getName());
		String reportViewPath = String.format("%1$s/%2$s/%3$s_%4$s.html", "/view", survey.getId(), reportName, ReportType.PDF.getName());
		String previewPath = String.format("%1$s/%2$s/%3$s_%4$s.html", "/preview", survey.getId(), reportName, ReportType.PDF.getName());
		
		// Update status and storage name
		String surveyStatus = preview != null? 
							  SurveyStatus.DRAFT.getValue() : 
						      (submit != null? SurveyStatus.COMPLETED.getValue() : SurveyStatus.PENDING.getValue());
		survey.setStatus(surveyStatus);
		survey.setStorageName(reportName);
		surveySvc.saveSurvey(survey);
		
		// Preview mode
		if (preview != null) {
			return "redirect:" + previewPath;
		}
		
		// Send notification email
		String baseUrl = ctx.getEnvironment().getProperty(ConfigConstant.BASE_URL.getName());
		String reportBasePath = ctx.getEnvironment().getProperty(ConfigConstant.REPORT_OUTPUT_BASE_URL.getName());
		String reportOutputPath = ctx.getEnvironment().getProperty(ConfigConstant.REPORT_OUTPUT_PATH.getName());
		String reportPath = String.format("%1$s%2$s%3$s%4$s%5$s.%6$s", reportBasePath, File.separator, ReportType.PDF.getName(), File.separator, reportName, ReportType.PDF.getName());
		String reportFilePath = String.format("%1$s%2$s%3$s%4$s%5$s.%6$s", reportOutputPath, File.separator, ReportType.PDF.getName(), File.separator, reportName, ReportType.PDF.getName());
		String reportUrl = String.format("%1$s%2$s", baseUrl, reportPath);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(ConfigConstant.SURVEY_PDF_URL.getName(), reportUrl);
		
		emailSvc.sendMail(survey, params, reportFilePath);
		logger.info("Send email for the survey " + survey.getId());
		
		return "redirect:" + reportViewPath;
	}
	
	@RequestMapping(value = { "/survey_{surveyTemplateId}.html" }, method = RequestMethod.GET)
	public String showSurvey(@PathVariable int surveyTemplateId) {
		String templatePath = ctx.getEnvironment().getProperty(ConfigConstant.TEMPLATE_PATH.getName());
		String prefixName = ctx.getEnvironment().getProperty(ConfigConstant.PREFIX_TEMPLATE_NAME.getName());
		String prefixTemplateDirName = ctx.getEnvironment().getProperty(ConfigConstant.TEMPLATE_FOLDER_NAME_PREFIX.getName());
		String surveyDirName = String.format("%1$s%2$s", prefixTemplateDirName, surveyTemplateId);
		String surveyName = String.format("%1$s%2$s", prefixName, surveyTemplateId);
		String surveyPath = String.format("%1$s%2$s%3$s%4$s%5$s", templatePath, File.separator, surveyDirName, File.separator, surveyName);
		
		logger.info("Process the survey " + surveyName);
		return surveyPath;
	}
	
	@RequestMapping(value = { "/survey_{surveyTemplateId}.html" }, method = RequestMethod.POST)
	public String saveSurvey(@PathVariable int surveyTemplateId, 
							 @ModelAttribute @Valid SurveyForm form,
							 @RequestParam(value = "preview", required = false) String preview,
							 @RequestParam(value = "submit", required = false) String submit,
							 BindingResult result, 
							 ModelMap model) {
		surveyTemplateId = 1;
		int surveyId = surveySvc.saveSurveyForm(surveyTemplateId, form);		
		
		// Redirect to error page with surveyId -1
		if (surveyId == -1) {
			return "redirect:/error.html";
		}
		
		// Export survey
		Survey survey = surveySvc.findSurveyWithId(surveyId);
		SurveyCalculate surveyCalc = calculationFactory.createSurveyCalculator(surveyTemplateId);
		surveyCalc.calculateFields(survey, surveySvc, ctx);
		// Refresh survey
		survey = surveySvc.findSurveyWithId(surveyId);
		// Set fulfilled date
		survey.setFulfilledDate();
		
		String reportName = reportSvc.exportReport(survey, ReportType.PDF.getName());
		String reportViewPath = String.format("%1$s/%2$s/%3$s_%4$s.html", "/view", survey.getId(), reportName, ReportType.PDF.getName());
		String previewPath = String.format("%1$s/%2$s/%3$s_%4$s.html", "/preview", survey.getId(), reportName, ReportType.PDF.getName());
		
		// Update status and storage name
		String surveyStatus = preview != null? 
							  SurveyStatus.DRAFT.getValue() : 
						      (submit != null? SurveyStatus.COMPLETED.getValue() : SurveyStatus.PENDING.getValue());
		survey.setStatus(surveyStatus);
		survey.setStorageName(reportName);
		surveySvc.saveSurvey(survey);
		
		// Preview mode
		if (preview != null) {
			return "redirect:" + previewPath;
		}
		
		// Send notification email
		String baseUrl = ctx.getEnvironment().getProperty(ConfigConstant.BASE_URL.getName());
		String reportBasePath = ctx.getEnvironment().getProperty(ConfigConstant.REPORT_OUTPUT_BASE_URL.getName());
		String reportOutputPath = ctx.getEnvironment().getProperty(ConfigConstant.REPORT_OUTPUT_PATH.getName());
		String reportPath = String.format("%1$s%2$s%3$s%4$s%5$s.%6$s", reportBasePath, File.separator, ReportType.PDF.getName(), File.separator, reportName, ReportType.PDF.getName());
		String reportFilePath = String.format("%1$s%2$s%3$s%4$s%5$s.%6$s", reportOutputPath, File.separator, ReportType.PDF.getName(), File.separator, reportName, ReportType.PDF.getName());
		String reportUrl = String.format("%1$s%2$s", baseUrl, reportPath);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(ConfigConstant.SURVEY_PDF_URL.getName(), reportUrl);
		
		emailSvc.sendMail(survey, params, reportFilePath);
		logger.info("Send email for the survey " + survey.getId());
		
		return "redirect:" + reportViewPath;
	}
	
	@RequestMapping(value = { "/error.html" }, method = RequestMethod.GET)
	public String showError(ModelMap model) {
		return "error";
	}
	
	@RequestMapping(value = { "/submit/{surveyId}.html" }, method = RequestMethod.POST)
	public String finishSurvey(ModelMap model,
							   @PathVariable int surveyId) {
		Survey survey = surveySvc.findSurveyWithId(surveyId);
		survey.setStatus(SurveyStatus.COMPLETED.getValue());
		surveySvc.saveSurvey(survey);
		String reportViewPath = String.format("%1$s/%2$s/%3$s_%4$s.html", "/view", survey.getId(), survey.getStorageName(), ReportType.PDF.getName());
		
		// Send notification email
		String baseUrl = ctx.getEnvironment().getProperty(ConfigConstant.BASE_URL.getName());
		String reportBasePath = ctx.getEnvironment().getProperty(ConfigConstant.REPORT_OUTPUT_BASE_URL.getName());
		String reportOutputPath = ctx.getEnvironment().getProperty(ConfigConstant.REPORT_OUTPUT_PATH.getName());
		String reportPath = String.format("%1$s%2$s%3$s%4$s%5$s.%6$s", reportBasePath, File.separator, ReportType.PDF.getName(), File.separator, survey.getStorageName(), ReportType.PDF.getName());
		String reportFilePath = String.format("%1$s%2$s%3$s%4$s%5$s.%6$s", reportOutputPath, File.separator, ReportType.PDF.getName(), File.separator, survey.getStorageName(), ReportType.PDF.getName());
		String reportUrl = String.format("%1$s%2$s", baseUrl, reportPath);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(ConfigConstant.SURVEY_PDF_URL.getName(), reportFilePath);
		
		emailSvc.sendMail(survey, params, reportUrl);
		logger.info("Send email for the survey " + survey.getId());
		
		return "redirect:" + reportViewPath;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/calculate_field_{surveyTemplateId}/{fieldName}.html" }, method = RequestMethod.POST)
	public @ResponseBody String calculateField (@PathVariable int surveyTemplateId,
												@PathVariable String fieldName,
												@RequestParam("fields['proposed-keyword']") MultipartFile uploadfile,
												@Valid SurveyForm form) {
		try {
			if (((MultipartFile)uploadfile).getBytes().length > 0) {
				String fileName = UUID.randomUUID().toString();
				String uploadPath = ctx.getEnvironment().getRequiredProperty(ConfigConstant.UPLOAD_FOLDER_PATH.getName());
				String filePath = String.format("%1$s%2$s%3$s", uploadPath, File.separator, fileName);
				File file = new File(filePath);
			
				FileUtils.writeByteArrayToFile(file, ((MultipartFile)uploadfile).getBytes());
				
				SurveyCalculate surveyCalc = calculationFactory.createSurveyCalculator(surveyTemplateId);
				String fieldValue = surveyCalc.calculateField(fieldName, file, form.getFields());
				
				JSONObject jsonObj = new JSONObject();
				jsonObj.put(fieldName, fieldValue);
				jsonObj.put(SurveyFieldName.UPLOADED_FILE_NAME.getName(), fileName);
				jsonObj.put("status", "success");
				
				return jsonObj.toJSONString();
			}
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			logger.error(e.getStackTrace());
		}
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", "error");
		
		return jsonObj.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/calculate_fields_{surveyTemplateId}/{fieldGroupName}.html" }, method = RequestMethod.POST)
	public @ResponseBody String calculateFields (@PathVariable int surveyTemplateId,
												@PathVariable String fieldGroupName,
												@RequestParam("fields['proposed-keyword']") MultipartFile uploadfile,
												@Valid SurveyForm form) {
		try {
			OfficeFileUtils fileUtils = new OfficeFileUtils();
			if (((MultipartFile)uploadfile).getBytes().length > 0) {
				String fileName = UUID.randomUUID().toString();
				String uploadPath = ctx.getEnvironment().getRequiredProperty(ConfigConstant.UPLOAD_FOLDER_PATH.getName());
				String filePath = String.format("%1$s%2$s%3$s", uploadPath, File.separator, fileName);
				File file = new File(filePath);
				
				if(uploadfile.getOriginalFilename().endsWith("ods")) {
					String tmpFilePath = filePath + ".ods";
					File tmpFile = new File(tmpFilePath);
					FileUtils.writeByteArrayToFile(tmpFile, ((MultipartFile)uploadfile).getBytes());
					byte[] fileByte = fileUtils.readOds(tmpFile);
					FileUtils.writeByteArrayToFile(file, fileByte);
					
				}else if(uploadfile.getOriginalFilename().endsWith("xlsx")) {
					String tmpFilePath = filePath + ".xlsx";
					File tmpFile = new File(tmpFilePath);
					FileUtils.writeByteArrayToFile(tmpFile, ((MultipartFile)uploadfile).getBytes());
					byte[] fileByte = fileUtils.readXlsx(tmpFile);
					FileUtils.writeByteArrayToFile(file, fileByte);
				}else {
					FileUtils.writeByteArrayToFile(file, ((MultipartFile)uploadfile).getBytes());
				}
				
				SurveyCalculate surveyCalc = calculationFactory.createSurveyCalculator(surveyTemplateId);
				Map<String, String> fieldValues = surveyCalc.calculateFields(fieldGroupName, file, form.getFields());
				
				JSONObject jsonObj = new JSONObject();
				
				Iterator<String> fieldKeys = fieldValues.keySet().iterator();
				while (fieldKeys.hasNext()) {
					String key = fieldKeys.next();
					
					jsonObj.put(key, fieldValues.get(key));
				}
				
				jsonObj.put(SurveyFieldName.UPLOADED_FILE_NAME.getName(), fileName);
				jsonObj.put("status", "success");
				
				return jsonObj.toJSONString();
			}
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			logger.error(e.getStackTrace());
		}
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", "error");
		
		return jsonObj.toJSONString();
	}
}
