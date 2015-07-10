package com.panpages.bow.model;

import java.util.HashMap;
import java.util.Map;

public class SurveyForm {
	
	private Map<String, Object> fields = new HashMap<String, Object>();

	public Map<String, Object> getFields() {
		return fields;
	}

	public void setFields(Map<String, Object> fields) {
		this.fields = fields;
	}
}
