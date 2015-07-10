package com.panpages.bow.testsuite;

import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.panpages.bow.service.report.ReportService;
import com.panpages.bow.service.report.ReportServiceImpl;
import com.panpages.bow.service.utils.BCryptUtils;

import junit.framework.TestCase;

public class SurveyTest extends TestCase {

	public void testExportReport() throws Exception {
		ReportService reportSvc = new ReportServiceImpl();
		String outputUrl = reportSvc.exportReport(1, "pdf");
		assertEquals(null, outputUrl);
	}
	
	public void testTimeZoneCurrentDate() {
		DateFormat format = DateFormat.getTimeInstance();
		TimeZone zone = TimeZone.getTimeZone("GMT+08:00");
		format.setTimeZone(zone);
		
		System.out.println(format.format(new Date()));
		
		assertEquals("GMT+08:00", format.getTimeZone().getID());
	}
	
	public void testBCryptGenerator() {
		boolean result = BCryptUtils.generateBCryptPassword("P@ss2105", 10);
		assertEquals(true, result);
	}
}
