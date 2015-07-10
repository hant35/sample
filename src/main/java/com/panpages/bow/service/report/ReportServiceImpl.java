package com.panpages.bow.service.report;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.panpages.bow.configuration.ConfigConstant;
import com.panpages.bow.model.Survey;

@Service("reportService")
public class ReportServiceImpl implements ReportService {
	@Autowired ApplicationContext ctx;
	
	@Override
	public String exportReport(Survey survey, String format) {
		if (survey == null) {
			return null;
		}
		
		return exportPDFFile(survey);
	}
	
	public String exportPDFFile(Survey survey) {
		String reportTemplateFolderPath = ctx.getEnvironment().getRequiredProperty(ConfigConstant.REPORT_TEMPLATE_PATH.getName());
		
		String prefixName = ctx.getEnvironment().getProperty(ConfigConstant.PREFIX_TEMPLATE_NAME.getName());
		String prefixReportDirName = ctx.getEnvironment().getProperty(ConfigConstant.TEMPLATE_FOLDER_NAME_PREFIX.getName());
		String reportDirName = String.format("%1$s%2$s", prefixReportDirName, survey.getSurveyTemplateId());
		String reportTemplateName = String.format("%1$s%2$s.%3$s", prefixName, survey.getSurveyTemplateId(), "jrxml");
		String reportTemplatePath = String.format("%1$s%2$s%3$s%4$s%5$s", reportTemplateFolderPath, File.separator, reportDirName, File.separator, reportTemplateName);
		String reportTemplateDirPath = String.format("%1$s%2$s%3$s", reportTemplateFolderPath, File.separator, reportDirName);
		
		String reportOutputPath = ctx.getEnvironment().getRequiredProperty(ConfigConstant.REPORT_OUTPUT_PATH.getName());
		String reportOutputDir = "pdf";
		String reportOutputFilename = String.format("%1$s%2$s%3$s", prefixName, survey.getSurveyTemplateId(), new Random().nextLong());
		String reportPDFFilename = String.format("%1$s.%2$s", reportOutputFilename, "pdf");
		String reportPDFFilePath = String.format("%1$s%2$s%3$s%4$s%5$s", reportOutputPath, File.separator, reportOutputDir, File.separator, reportPDFFilename);
		
		List<Survey> surveys = new ArrayList<Survey>();
		surveys.add(survey);
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("SUBREPORT_DIR", reportTemplateDirPath);
		try {
			JasperDesign jasperDesign = JRXmlLoader.load(reportTemplatePath);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			JasperPrint print = JasperFillManager.fillReport(jasperReport, params, new JRBeanCollectionDataSource(surveys));
			JasperExportManager.exportReportToPdfFile(print, reportPDFFilePath);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return reportOutputFilename;
	}

	@Override
	public String exportReport(int surveyId, String name) {
		//throw new UnsupportedOperationException();
		return null;
	}

}
