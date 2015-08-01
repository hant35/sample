package com.panpages.bow.service.survey;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.panpages.bow.model.Field;
import com.panpages.bow.model.FieldTemplate;
import com.panpages.bow.model.Survey;
import com.panpages.bow.model.SurveyForm;
import com.panpages.bow.model.SurveyTemplate;
import com.panpages.bow.model.SystemConfig;

public interface SurveyService {
	
	void saveSurvey(Survey survey);
	List<Survey> findAllSurveys(); 
	List<SurveyTemplate> findAllSurveyTemplates();
	int saveSurveyForm(int surveyTemplateId, SurveyForm form, Date timeAccess);
	Survey findSurveyWithId(int surveyId); 
	FieldTemplate findFieldTemplateWithSlugName(String slugName);
	SurveyTemplate findSurveyTemplateWithId(int surveyTemplateId);
	int saveSurveyForm(int surveyTemplateId, SurveyForm form, SurveyCalculate calculation);
	int addField(String fieldName, String fieldValue, Survey survey);
	List<Survey> findSurveyByMonthYear(Date date);
	List<Survey> findSurveyByMonthYear(Calendar fromDate, Calendar toDate);
	Field findFieldByName(int id, String name);
	SystemConfig getSystemConfig(String key);
	int saveSystemConfig(SystemConfig systemConfig);
}
