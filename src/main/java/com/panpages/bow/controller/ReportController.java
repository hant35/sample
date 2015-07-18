package com.panpages.bow.controller;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.panpages.bow.configuration.ConfigConstant;
import com.panpages.bow.model.Field;
import com.panpages.bow.model.Survey;
import com.panpages.bow.model.utils.SurveyUtils;
import com.panpages.bow.service.report.ReportService;
import com.panpages.bow.service.survey.SurveyService;

@Controller
@RequestMapping("/")
public class ReportController {

	@Autowired ApplicationContext ctx;
	
	@Autowired
	SurveyService surveySvc;
	
	@Autowired
	ReportService reportSvc;
	
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
	
}
