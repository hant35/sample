package com.panpages.bow.service.survey;

import java.io.File;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.panpages.bow.model.Survey;

public interface SurveyCalculate {
	
	public String calculateField(String fieldName, File fieldsFile, Map<String, Object> map);
	void calculateFields(Survey survey, SurveyService surveySvc, ApplicationContext ctx);
	public Map<String, String> calculateFields(String fieldName, File fieldsFile, Map<String, Object> params);
	
}
