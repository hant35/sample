package com.panpages.bow.service.survey;

public enum SurveyFieldName {
	
	FIELD_EST_MONTHLY_INVESTMENT("est-monthly-investment"),
	FIELD_TOTAL_EST_IMPRESSIONS("total-est-impressions"),
	FIELD_EST_CLICKS_ON_CTR("est-clicks-on-ctr"), 
	FIELD_CAMPAIGN_CLICK_THROUGH_RATE("campaign-click-through-rate"),
	FIELD_PROPOSED_KEYWORD("proposed-keyword"),
	FIELD_TOTAL_CAMPAIGN_BUDGET("total-campaign-budget"),
	
	SECTION_KEYWORD_PLAN("section-9"), 
	STEP_5_H("step-5-h"), 
	STEP_5_D("step-5-d"), 
	STEP_5_A("step-5-a"), 
	STEP_5_B("step-5-b"), 
	
	UPLOADED_FILE_NAME("uploaded-file-name"), 
	FIELD_TOTAL_GOOGLE_BUDGET("total-google-budget");
	
	private String name;
	
	SurveyFieldName(String value) {
		this.name = value;
	}

	public String getName() {
		return name;
	}
			
}
