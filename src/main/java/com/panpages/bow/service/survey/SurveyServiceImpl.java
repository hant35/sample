package com.panpages.bow.service.survey;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panpages.bow.dao.SurveyDao;
import com.panpages.bow.model.Field;
import com.panpages.bow.model.FieldTemplate;
import com.panpages.bow.model.Survey;
import com.panpages.bow.model.SurveyForm;
import com.panpages.bow.model.SurveyTemplate;

@Service("surveyService")
@Transactional
public class SurveyServiceImpl implements SurveyService{

	@Autowired
	private SurveyDao dao;
	
	public void saveSurvey(Survey survey) {
		dao.saveSurvey(survey);
	}

	public List<Survey> findAllSurveys() {
		return dao.findAllSurveys();
	}

	@Override
	public List<SurveyTemplate> findAllSurveyTemplates() {
		return dao.findAllSurveyTemplates();
	}

	@Override
	public int saveSurveyForm(int surveyTemplateId, SurveyForm form) {
		if (form == null) {
			return -1;
		}
		
		return dao.saveSurveyForm(surveyTemplateId, form);
	}
	
	@Override
	public int saveSurveyForm(int surveyTemplateId, SurveyForm form, SurveyCalculate calculation) {
		if (form == null) {
			return -1;
		}
		
		return dao.saveSurveyForm(surveyTemplateId, form, calculation);
	}

	@Override
	public FieldTemplate findFieldTemplateWithSlugName(String slugName) {
		return dao.findFieldTemplateWithSlugName(slugName);
	}

	@Override
	public Survey findSurveyWithId(int surveyId) {
		return dao.findSurveyWithId(surveyId);
	}

	@Override
	public SurveyTemplate findSurveyTemplateWithId(int surveyTemplateId) {
		return dao.findSurveyTemplateWithId(surveyTemplateId);
	}

	@Override
	public int addField(String fieldName, String fieldValue, Survey survey) {
		return dao.addField(fieldName, fieldValue, survey);
	}
	
	@Override
	public List<Survey> findSurveyByMonthYear(Date date){
		return dao.findSurveyByMonthYear(date);
	}

	@Override
	public Field findFieldByName(int id, String name) {
		return dao.findFieldByName(id, name);
	}
}
