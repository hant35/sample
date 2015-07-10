package com.panpages.bow.service.survey;

public enum SurveyStatus {
	
	PENDING("pending"),
	DRAFT("draft"),
	COMPLETED("completed");
	
	private String value;
	
	SurveyStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
