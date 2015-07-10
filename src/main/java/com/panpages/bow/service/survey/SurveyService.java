package com.panpages.bow.service.survey;

import java.util.List;

import com.panpages.bow.model.FieldTemplate;
import com.panpages.bow.model.Survey;
import com.panpages.bow.model.SurveyForm;
import com.panpages.bow.model.SurveyTemplate;

public interface SurveyService {
	
	void saveSurvey(Survey survey);
	List<Survey> findAllSurveys(); 
	List<SurveyTemplate> findAllSurveyTemplates();
	int saveSurveyForm(int surveyTemplateId, SurveyForm form);
	Survey findSurveyWithId(int surveyId); 
	FieldTemplate findFieldTemplateWithSlugName(String slugName);
	SurveyTemplate findSurveyTemplateWithId(int surveyTemplateId);
	int saveSurveyForm(int surveyTemplateId, SurveyForm form, SurveyCalculate calculation);
	int addField(String fieldName, String fieldValue, Survey survey);
	
}
