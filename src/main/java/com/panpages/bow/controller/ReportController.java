package com.panpages.bow.controller;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.panpages.bow.configuration.ConfigConstant;
import com.panpages.bow.model.CustomerSurveys;
import com.panpages.bow.model.Field;
import com.panpages.bow.model.Report;
import com.panpages.bow.model.Survey;
import com.panpages.bow.model.utils.SurveyUtils;
import com.panpages.bow.service.mail.MailService;
import com.panpages.bow.service.report.ReportService;
import com.panpages.bow.service.survey.CustomerService;
import com.panpages.bow.service.survey.SurveyService;
import com.panpages.bow.service.utils.OfficeFileUtils;

@Controller
@RequestMapping("/")
public class ReportController {

	@Autowired ApplicationContext ctx;
	
	@Autowired
	CustomerService customerSvc;
	
	@Autowired
	SurveyService surveySvc;
	
	@Autowired
	ReportService reportSvc;
	
	@Autowired
	MailService emailSvc;
	
	private static final Logger logger = Logger.getLogger(ReportController.class);
	
	@RequestMapping(value = { "/view/{surveyId}/{outputId}_{outputType}.html" }, method = RequestMethod.GET)
	public String viewReport(@PathVariable int surveyId,
							 @PathVariable String outputId, 
							 @PathVariable String outputType, 
							 ModelMap model) {
		logger.info(String.format("View the survey %1$s with format %2$s", outputId, outputType));
		
		Survey survey = surveySvc.findSurveyWithId(surveyId);
		Field consultantMailField = SurveyUtils.findFieldWithFieldTemplateSlugName(ConfigConstant.CONSULTANT_EMAIL.getName(), survey);						
		String consultantMail = consultantMailField.getValue();
		
		String reportBasePath = ctx.getEnvironment().getProperty(ConfigConstant.REPORT_OUTPUT_BASE_URL.getName());
		String basePath = ctx.getEnvironment().getProperty(ConfigConstant.BASE_URL.getName());
		String reportPath = String.format("%1$s%2$s%3$s%4$s%5$s.%6$s", reportBasePath, "" +"/", outputType, "" +"/", outputId, outputType);
		model.addAttribute("surveyId", surveyId);
		model.addAttribute("reportPath", basePath + reportPath);
		model.addAttribute("email", consultantMail);
		return "view_report";
	}
	
	@RequestMapping(value = { "/preview/{surveyId}/{outputId}_{outputType}.html" }, method = RequestMethod.GET)
	public String previewReport(@PathVariable int surveyId,
								@PathVariable String outputId, 
								@PathVariable String outputType, 
								ModelMap model) {
		logger.info(String.format("View the survey %1$s with format %2$s", outputId, outputType));
		
		Survey survey = surveySvc.findSurveyWithId(surveyId);
		Field consultantMailField = SurveyUtils.findFieldWithFieldTemplateSlugName(ConfigConstant.CONSULTANT_EMAIL.getName(), survey);						
		String consultantMail = consultantMailField.getValue();
		
		String reportBasePath = ctx.getEnvironment().getProperty(ConfigConstant.REPORT_OUTPUT_BASE_URL.getName());
		String reportPath = String.format("%1$s%2$s%3$s%4$s%5$s.%6$s", reportBasePath, "" +"/", outputType, "" +"/", outputId, outputType);
		String BasePath = ctx.getEnvironment().getProperty(ConfigConstant.BASE_URL.getName());
		model.addAttribute("surveyId", surveyId);
		model.addAttribute("reportPath", reportPath);
		model.addAttribute("isPreview", true);
		model.addAttribute("email", consultantMail);
		model.addAttribute("outputId", outputId);
		model.addAttribute("outputType", outputType);
		
		return "view_report";
	}
	
	@RequestMapping(value = { "/download/{outputId}/{outputType}.html" }, method = RequestMethod.GET)
	public void download(ModelMap model, @PathVariable String outputId, 
			 @PathVariable String outputType, HttpServletResponse response) {
		try {
			String reportOutputPath = ctx.getEnvironment().getProperty(ConfigConstant.REPORT_OUTPUT_PATH.getName());
			String filePath = String.format("%1$s%2$s%3$s%4$s%5$s.%6$s", reportOutputPath,  "" +"/",outputType, "" +"/",outputId, outputType);
			
			File fileTemp = new File(filePath);
	         FileInputStream fileInputStream = new FileInputStream(fileTemp);
	         byte[] arr = new byte[1024];
	         int numRead = -1;

	         response.addHeader("Content-Length", Long.toString(fileTemp.length()));
	         response.setContentType("application/octet-stream");
	         response.addHeader("Content-Disposition", "inline; filename=\"" + fileTemp.getName() + "\"");
	         while ((numRead = fileInputStream.read(arr)) != -1) {
	             response.getOutputStream().write(arr, 0, numRead);
	         }
	         fileInputStream.close();
	         //fileTemp.delete();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	@RequestMapping(value = { "/report/{key}/out/{action}.html" }, method = RequestMethod.GET)
	public void report(ModelMap model, @PathVariable String key, @PathVariable String action,
			  HttpServletResponse response) throws Exception {
		String reportKey = ctx.getEnvironment().getProperty(ConfigConstant.EXCEL_REPORT_KEY.getName());
		 try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(reportKey.getBytes());
	        byte byteData[] = md.digest();
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	        	sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        String hashKey = sb.toString();
	        if(!hashKey.equals(key)) {
	        	throw new Exception("Key not match.");
	        }
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
			return;
		}
		 
		String excelOutputPath = ctx.getEnvironment().getProperty(ConfigConstant.EXCEL_OUTPUT_PATH.getName());
		Calendar cal = Calendar.getInstance();
		//cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
	    List<Report> lstResult = new ArrayList<Report>();
		List<Survey> surveys = surveySvc.findSurveyByMonthYear(cal.getTime());
		for (Survey survey : surveys) {
			
			Report result = new Report();
			String Email = surveySvc.findFieldByName(survey.getId(), "Email Address").getValue();
			result.setEmail(Email);
			String UserName = surveySvc.findFieldByName(survey.getId(), "Consultant Name").getValue();
			result.setUserName(UserName);
			result.setTimeAccess(survey.getTimeAccess());
			result.setTimeReceived(survey.getDate());
			if(survey.getStorageName() != null){
				String[] tmp = survey.getStorageName().split("_");
				if(tmp != null && tmp.length > 2) {
					CustomerSurveys cusSur = customerSvc.findSurveyByCusSurTemplate(tmp[0] + "_" + tmp[1]);
					if(cusSur != null){
					String typeSurvey = cusSur.getName();
					result.setType(typeSurvey);
					
					}else{
						result.setType(survey.getName());
					}
				}
			}else{
				result.setType("");
			}
			lstResult.add(result);
		}
		OfficeFileUtils fileUtils = new OfficeFileUtils();
		String reportFile = fileUtils.createExcelFile(excelOutputPath, (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR), lstResult);
		if(action.equals("dl")){
			File fileTemp = new File(reportFile);
	         FileInputStream fileInputStream = new FileInputStream(fileTemp);
	         byte[] arr = new byte[1024];
	         int numRead = -1;

	         response.addHeader("Content-Length", Long.toString(fileTemp.length()));
	         response.setContentType("application/octet-stream");
	         response.addHeader("Content-Disposition", "inline; filename=\"" + fileTemp.getName() + "\"");
	         while ((numRead = fileInputStream.read(arr)) != -1) {
	             response.getOutputStream().write(arr, 0, numRead);
	         }
	         fileInputStream.close();
		}else if (action.equals("mail")) {
			String reportMailTo = ctx.getEnvironment().getProperty(ConfigConstant.EXCEL_REPORT_MAILTO.getName());
			String[] mailTo = reportMailTo.split(",");
			emailSvc.sendMail(mailTo, "Report " + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR),reportFile);
		}else if (action.equals("all")){
			try {
				String reportMailTo = ctx.getEnvironment().getProperty(ConfigConstant.EXCEL_REPORT_MAILTO.getName());
				String[] mailTo = reportMailTo.split(",");
				emailSvc.sendMail(mailTo, "Report " + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR),reportFile);	
			} catch (Exception e) {
			
			}
			File fileTemp = new File(reportFile);
	         FileInputStream fileInputStream = new FileInputStream(fileTemp);
	         byte[] arr = new byte[1024];
	         int numRead = -1;

	         response.addHeader("Content-Length", Long.toString(fileTemp.length()));
	         response.setContentType("application/octet-stream");
	         response.addHeader("Content-Disposition", "inline; filename=\"" + fileTemp.getName() + "\"");
	         while ((numRead = fileInputStream.read(arr)) != -1) {
	             response.getOutputStream().write(arr, 0, numRead);
	         }
	         fileInputStream.close();
		}
	}
	
	@RequestMapping(value = { "/report/out.html" }, method = RequestMethod.POST)
	public void reportFormTo(ModelMap model,@ModelAttribute(value = "bean") Report report, @PathVariable String action,
			  HttpServletResponse response) throws Exception {
		String excelOutputPath = ctx.getEnvironment().getProperty(ConfigConstant.EXCEL_OUTPUT_PATH.getName());
		
	    List<Report> lstResult = new ArrayList<Report>();
	    if(report != null && report.getFromDate() != null && report.getToDate()!= null ) {
	    	Calendar fromDate = Calendar.getInstance();
			fromDate.setTime(report.getFromDate());
			Calendar toDate = Calendar.getInstance();
			toDate.setTime(report.getToDate());
			
	    	List<Survey> surveys = surveySvc.findSurveyByMonthYear(fromDate, toDate);
			for (Survey survey : surveys) {
				
				Report result = new Report();
				String Email = surveySvc.findFieldByName(survey.getId(), "Email Address").getValue();
				result.setEmail(Email);
				String UserName = surveySvc.findFieldByName(survey.getId(), "Consultant Name").getValue();
				result.setUserName(UserName);
				result.setTimeAccess(survey.getTimeAccess());
				result.setTimeReceived(survey.getDate());
				if(survey.getStorageName() != null){
					String[] tmp = survey.getStorageName().split("_");
					if(tmp != null && tmp.length > 2) {
						CustomerSurveys cusSur = customerSvc.findSurveyByCusSurTemplate(tmp[0] + "_" + tmp[1]);
						if(cusSur != null){
						String typeSurvey = cusSur.getName();
						result.setType(typeSurvey);
						
						}else{
							result.setType(survey.getName());
						}
					}
				}else{
					result.setType("");
				}
				lstResult.add(result);
			}
			OfficeFileUtils fileUtils = new OfficeFileUtils();
			String reportFile = fileUtils.createExcelFile(excelOutputPath, (fromDate.getTime().toString() + " - " + toDate.getTime().toString()), lstResult);
			if(action.equals("dl")){
				File fileTemp = new File(reportFile);
		         FileInputStream fileInputStream = new FileInputStream(fileTemp);
		         byte[] arr = new byte[1024];
		         int numRead = -1;

		         response.addHeader("Content-Length", Long.toString(fileTemp.length()));
		         response.setContentType("application/octet-stream");
		         response.addHeader("Content-Disposition", "inline; filename=\"" + fileTemp.getName() + "\"");
		         while ((numRead = fileInputStream.read(arr)) != -1) {
		             response.getOutputStream().write(arr, 0, numRead);
		         }
		         fileInputStream.close();
			}else if (action.equals("mail")) {
				String reportMailTo = ctx.getEnvironment().getProperty(ConfigConstant.EXCEL_REPORT_MAILTO.getName());
				String[] mailTo = reportMailTo.split(",");
				emailSvc.sendMail(mailTo, "Report " + (fromDate.getTime().toString() + " - " + toDate.getTime().toString()),reportFile);
			}else if (action.equals("all")){
				try {
					String reportMailTo = ctx.getEnvironment().getProperty(ConfigConstant.EXCEL_REPORT_MAILTO.getName());
					String[] mailTo = reportMailTo.split(",");
					emailSvc.sendMail(mailTo, "Report " + (fromDate.getTime().toString() + " - " + toDate.getTime().toString()),reportFile);	
				} catch (Exception e) {
				
				}
				File fileTemp = new File(reportFile);
		         FileInputStream fileInputStream = new FileInputStream(fileTemp);
		         byte[] arr = new byte[1024];
		         int numRead = -1;

		         response.addHeader("Content-Length", Long.toString(fileTemp.length()));
		         response.setContentType("application/octet-stream");
		         response.addHeader("Content-Disposition", "inline; filename=\"" + fileTemp.getName() + "\"");
		         while ((numRead = fileInputStream.read(arr)) != -1) {
		             response.getOutputStream().write(arr, 0, numRead);
		         }
		         fileInputStream.close();
			}
	    }
		
	}
}
