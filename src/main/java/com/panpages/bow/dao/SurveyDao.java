package com.panpages.bow.dao;

import java.util.List;

import com.panpages.bow.model.Field;
import com.panpages.bow.model.FieldAndSectionRelation;
import com.panpages.bow.model.FieldTemplate;
import com.panpages.bow.model.Section;
import com.panpages.bow.model.SectionAndSurveyRelation;
import com.panpages.bow.model.SectionTemplate;
import com.panpages.bow.model.Survey;
import com.panpages.bow.model.SurveyForm;
import com.panpages.bow.model.SurveyTemplate;
import com.panpages.bow.service.survey.SurveyCalculate;

public interface SurveyDao {

	void saveSurvey(Survey survey);
	List<Survey> findAllSurveys();
	List<SurveyTemplate> findAllSurveyTemplates();
	Survey findSurveyWithId(int surveyId);
	FieldTemplate findFieldTemplateWithSlugName(String slugName);
	void saveField(Field field);
	void saveSection(Section section);
	int saveSurveyForm(int templateId, SurveyForm form);
	void saveSectionAndSurveyRelation(SectionAndSurveyRelation relation);
	void saveFieldAndSectionRelation(FieldAndSectionRelation relation);
    SurveyTemplate findSurveyTemplateWithId(int templateId);
    List<SectionTemplate> findSectionTemplatesBelongToSurveyTemplate(int surveyTemplateId);
	int saveSurveyForm(int surveyTemplateId, SurveyForm form, SurveyCalculate calculation);
	int addField(String fieldName, String fieldValue, Survey survey);
	
}
