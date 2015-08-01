package com.panpages.bow.controller;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.panpages.bow.configuration.ConfigConstant;
import com.panpages.bow.model.CustomerSurveys;
import com.panpages.bow.model.Field;
import com.panpages.bow.model.Report;
import com.panpages.bow.model.Survey;
import com.panpages.bow.model.SystemConfig;
import com.panpages.bow.model.utils.SurveyUtils;
import com.panpages.bow.service.mail.MailService;
import com.panpages.bow.service.report.ReportService;
import com.panpages.bow.service.survey.CustomerService;
import com.panpages.bow.service.survey.SurveyService;
import com.panpages.bow.service.utils.OfficeFileUtils;

@Controller
@RequestMapping("/")
public class ReportController {

	@Autowired
	ApplicationContext ctx;

	@Autowired
	CustomerService customerSvc;

	@Autowired
	SurveyService surveySvc;

	@Autowired
	ReportService reportSvc;

	@Autowired
	MailService emailSvc;

	private static final Logger logger = Logger.getLogger(ReportController.class);
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping(value = { "/view/{surveyId}/{outputId}_{outputType}.html" }, method = RequestMethod.GET)
	public String viewReport(@PathVariable int surveyId, @PathVariable String outputId, @PathVariable String outputType,
			ModelMap model) {
		logger.info(String.format("View the survey %1$s with format %2$s", outputId, outputType));

		Survey survey = surveySvc.findSurveyWithId(surveyId);
		Field consultantMailField = SurveyUtils
				.findFieldWithFieldTemplateSlugName(ConfigConstant.CONSULTANT_EMAIL.getName(), survey);
		String consultantMail = consultantMailField.getValue();

		String reportBasePath = ctx.getEnvironment().getProperty(ConfigConstant.REPORT_OUTPUT_BASE_URL.getName());
		String basePath = ctx.getEnvironment().getProperty(ConfigConstant.BASE_URL.getName());
		String reportPath = String.format("%1$s%2$s%3$s%4$s%5$s.%6$s", reportBasePath, "" + "/", outputType, "" + "/",
				outputId, outputType);
		model.addAttribute("surveyId", surveyId);
		model.addAttribute("reportPath", basePath + reportPath);
		model.addAttribute("email", consultantMail);
		return "view_report";
	}

	@RequestMapping(value = { "/preview/{surveyId}/{outputId}_{outputType}.html" }, method = RequestMethod.GET)
	public String previewReport(@PathVariable int surveyId, @PathVariable String outputId,
			@PathVariable String outputType, ModelMap model) {
		logger.info(String.format("View the survey %1$s with format %2$s", outputId, outputType));

		Survey survey = surveySvc.findSurveyWithId(surveyId);
		Field consultantMailField = SurveyUtils
				.findFieldWithFieldTemplateSlugName(ConfigConstant.CONSULTANT_EMAIL.getName(), survey);
		String consultantMail = consultantMailField.getValue();

		String reportBasePath = ctx.getEnvironment().getProperty(ConfigConstant.REPORT_OUTPUT_BASE_URL.getName());
		String reportPath = String.format("%1$s%2$s%3$s%4$s%5$s.%6$s", reportBasePath, "" + "/", outputType, "" + "/",
				outputId, outputType);
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
	public void download(ModelMap model, @PathVariable String outputId, @PathVariable String outputType,
			HttpServletResponse response) {
		try {
			String reportOutputPath = ctx.getEnvironment().getProperty(ConfigConstant.REPORT_OUTPUT_PATH.getName());
			String filePath = String.format("%1$s%2$s%3$s%4$s%5$s.%6$s", reportOutputPath, "" + "/", outputType,
					"" + "/", outputId, outputType);

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
			// fileTemp.delete();
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
			if (!hashKey.equals(key)) {
				throw new Exception("Key not match.");
			}
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
			return;
		}

		String excelOutputPath = ctx.getEnvironment().getProperty(ConfigConstant.EXCEL_OUTPUT_PATH.getName());
		Calendar cal = Calendar.getInstance();
		// cal.set(Calendar.YEAR, year);
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
			if (survey.getStorageName() != null) {
				String[] tmp = survey.getStorageName().split("_");
				if (tmp != null && tmp.length > 2) {
					CustomerSurveys cusSur = customerSvc.findSurveyByCusSurTemplate(tmp[0] + "_" + tmp[1]);
					if (cusSur != null) {
						String typeSurvey = cusSur.getName();
						result.setType(typeSurvey);

					} else {
						result.setType(survey.getName());
					}
				}
			} else {
				result.setType("");
			}
			lstResult.add(result);
		}
		OfficeFileUtils fileUtils = new OfficeFileUtils();
		String reportFile = fileUtils.createExcelFile(excelOutputPath,
				(cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR), lstResult);
		if (action.equals("dl")) {
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
		} else if (action.equals("mail")) {
			String reportMailTo = ctx.getEnvironment().getProperty(ConfigConstant.EXCEL_REPORT_MAILTO.getName());
			SystemConfig config = surveySvc.getSystemConfig("excel_mailto");
			if(config !=null && config.getValue() != null) {
				reportMailTo = config.getValue();
			}
			
			String[] mailTo = reportMailTo.split(",");
			emailSvc.sendMail(mailTo, "Report " + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR),
					reportFile);
		} else if (action.equals("all")) {
			try {
				String reportMailTo = ctx.getEnvironment().getProperty(ConfigConstant.EXCEL_REPORT_MAILTO.getName());
				String[] mailTo = reportMailTo.split(",");
				emailSvc.sendMail(mailTo, "Report " + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR),
						reportFile);
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

	@RequestMapping(value = { "/report_out.html" }, method = RequestMethod.POST)
	public void reportFormTo(Model model, @ModelAttribute(value = "report") @Valid Report report,
			HttpServletResponse response, BindingResult bindingResult) throws Exception {
		String excelOutputPath = ctx.getEnvironment().getProperty(ConfigConstant.EXCEL_OUTPUT_PATH.getName());
		String action = report.getAction();
		List<Report> lstResult = new ArrayList<Report>();
		if (report != null && report.getsFromDate() != null && report.getsToDate() != null) {
			report.setFromDate(formatter.parse(report.getsFromDate()));
			report.setToDate(formatter.parse(report.getsToDate()));
			Calendar fromDate = Calendar.getInstance();
			fromDate.setTime(formatter.parse(report.getsFromDate()));
			Calendar toDate = Calendar.getInstance();
			toDate.setTime(report.getToDate());

			if(fromDate.after(toDate)) {
				report.setResult("From date must be before to date");
				return;
			}
			
			List<Survey> surveys = surveySvc.findSurveyByMonthYear(fromDate, toDate);
			for (Survey survey : surveys) {

				Report result = new Report();
				String Email = surveySvc.findFieldByName(survey.getId(), "Email Address").getValue();
				result.setEmail(Email);
				String UserName = surveySvc.findFieldByName(survey.getId(), "Consultant Name").getValue();
				result.setUserName(UserName);
				result.setTimeAccess(survey.getTimeAccess());
				result.setTimeReceived(survey.getDate());
				if (survey.getStorageName() != null) {
					String[] tmp = survey.getStorageName().split("_");
					if (tmp != null && tmp.length > 2) {
						CustomerSurveys cusSur = customerSvc.findSurveyByCusSurTemplate(tmp[0] + "_" + tmp[1]);
						if (cusSur != null) {
							String typeSurvey = cusSur.getName();
							result.setType(typeSurvey);

						} else {
							result.setType(survey.getName());
						}
					}
				} else {
					result.setType("");
				}
				lstResult.add(result);
			}
			OfficeFileUtils fileUtils = new OfficeFileUtils();
			SimpleDateFormat formatter1 = new SimpleDateFormat("ddMMyyyy");
			String sheetName = formatter1.format(report.getFromDate()) + " - " + formatter1.format(report.getToDate());
			String reportFile = fileUtils.createExcelFile(excelOutputPath, sheetName, lstResult);
			SystemConfig config = surveySvc.getSystemConfig("excel_mailto");
			String reportMailTo = report.getMailTo();
			if(reportMailTo.trim().length() > 0) {
			try {
				SystemConfig tmpConfig = new SystemConfig();
				tmpConfig.setKey("excel_mailto");
				tmpConfig.setValue(reportMailTo);
				surveySvc.saveSystemConfig(tmpConfig);
			} catch (Exception e) {
				e.printStackTrace();
			}
					//ctx.getEnvironment().getProperty(ConfigConstant.EXCEL_REPORT_MAILTO.getName());
			}else{
				reportMailTo = config.getValue();
			}
			
			
			if (action.equals("dl")) {
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
				
			} else if (action.equals("mail")) {
				
				String[] mailTo = reportMailTo.split(",");
				emailSvc.sendMail(mailTo,
						"Report " + sheetName, reportFile);
				response.sendRedirect("admin.html?success");
			} else if (action.equals("all")) {
				try {
					
					String[] mailTo = reportMailTo.split(",");
					emailSvc.sendMail(mailTo,
							"Report " + (fromDate.getTime().toString() + " - " + toDate.getTime().toString()),
							reportFile);
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
			report.setResult("Completed");
			
		}else{
			report.setResult("Please select value.");
		}
		model.addAttribute("report", report);

	}
}
