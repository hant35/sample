package com.panpages.bow.model.utils;

import java.util.ArrayList;
import java.util.List;

import com.panpages.bow.model.Field;
import com.panpages.bow.model.FieldTemplate;
import com.panpages.bow.model.Section;
import com.panpages.bow.model.SectionTemplate;
import com.panpages.bow.model.Survey;
import com.panpages.bow.model.SurveyBean;

public class SurveyUtils {
	
	public static Section findSectionWithSectionTemplateSlugName(String slugName, List<Section> sectionSet) {
		if (slugName == null || slugName.trim().equals("") || sectionSet.size() == 0) {
			return null;
		}
		
		for (Section section:sectionSet) {
			SectionTemplate sectionTempl = section.getSectionTemplate();
			String templSlugName = sectionTempl.getSlugName();
			if (slugName.equalsIgnoreCase(templSlugName)) {
				return section;
			}
		}
		
		return null;
	}
	
	public static Field findFieldWithFieldTemplateSlugName(String slugName, List<Field> fields) {
		Field emptyField = new Field();
		emptyField.setId(-1);
		
		if (slugName == null || slugName.trim().equals("") || fields.size() == 0) {
			return emptyField;
		}
		
		for (Field field:fields) {
			if (field != null && field.getId() != -1) {
				FieldTemplate fieldTempl = field.getFieldTemplate();
				String templSlugName = fieldTempl.getSlugName();
				if (slugName.equalsIgnoreCase(templSlugName)) {
					return field;
				}
			}
		}
		
		return emptyField;
	}
	
	public static List<Field> findFieldsWithFieldTemplateSlugName(String slugName, List<Field> fields) {
		if (slugName == null || slugName.trim().equals("") || fields.size() == 0) {
			return null;
		}
		
		List<Field> resultFields = new ArrayList<Field>();
		for (Field field:fields) {
			if (field != null && field.getId() != -1) {
				FieldTemplate fieldTempl = field.getFieldTemplate();
				String templSlugName = fieldTempl.getSlugName();
				if (slugName.equalsIgnoreCase(templSlugName)) {
					resultFields.add(field);
				}
			}
		}
		
		return resultFields;
	}
	
	public static List<Field> findFieldsWithFieldTemplateSlugName(String slugName, List<Field> fields, String value) {
		if (slugName == null || slugName.trim().equals("") || fields.size() == 0) {
			return null;
		}
		
		if (value == null) {
			return findFieldsWithFieldTemplateSlugName(slugName, fields);
		}
		
		List<Field> resultFields = new ArrayList<Field>();
		for (Field field:fields) {
			if (field != null && field.getId() != -1) {
				FieldTemplate fieldTempl = field.getFieldTemplate();
				String templSlugName = fieldTempl.getSlugName();
				if (slugName.equalsIgnoreCase(templSlugName) & field.getValue().equalsIgnoreCase(value)) {
					resultFields.add(field);
				}
			}
		}
		
		return resultFields;
	}
	
	public static SurveyBean createSurveyBean(List<Field> fields) {
		SurveyBean bean = new SurveyBean(fields);
		
		return bean;
	}
	
	public static List<SurveyBean> createSurveyBeans(SurveyBean... beans) {
		List<SurveyBean> beanList = new ArrayList<SurveyBean>();
		
		for (SurveyBean bean : beans) {
			beanList.add(bean);
		}
		
		return beanList;
	}

	public static Field findFieldWithFieldTemplateSlugName(String fieldSlugName, Survey survey) { 
		if (fieldSlugName == null || survey == null) {
			return null;
		}
		
		List<Section> sections = survey.getSections();
		for (Section section : sections) {
			Field field = findFieldWithFieldTemplateSlugName(fieldSlugName, section.getFields());
			
			if (field != null && field.getId() != -1) {
				FieldTemplate fieldTempl = field.getFieldTemplate();
				String templSlugName = fieldTempl.getSlugName();
				if (templSlugName.equalsIgnoreCase(fieldSlugName)) {
					return field;
				}
			}
		}
		
		return null;
	}
	
}
