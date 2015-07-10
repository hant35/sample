package com.panpages.bow.model;

import java.util.List;

public class SurveyBean {
	private List<Field> fields;
	
	public SurveyBean(List<Field> fields) {
		this.setFields(fields);
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	public String joinFields(String delimiter) {
		if (fields == null || fields.size() == 0) {
			return null;
		}
		
		StringBuilder result = new StringBuilder();
		int count = 0;
		for (Field field:fields) {
			if (count++ > 0 && delimiter != null) {
				result.append(delimiter);
			}
			
			result.append(" " + field.getValue());
		}
		
		return result.toString();
	}
}
