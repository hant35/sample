package com.panpages.bow.service.report;

import com.panpages.bow.model.Survey;

public interface ReportService {
	
	String exportReport(Survey survey, String name);
	String exportReport(int surveyId, String name);

}


