package com.panpages.bow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Customer_survey")
public class CustomerSurveys {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CUSTOMER_SURVEY_ID", nullable = false)
	private int customerSurveyId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "CUSTOMER_ID")
    private int customerId;
	
	@Column(name = "SURVEY_LIST")
	private String surveyList;

	@Column(name = "TEMPLATE")
	private String template;
	
	public int getCustomerSurveyId() {
		return customerSurveyId;
	}

	public void setCustomerSurveyId(int customerSurveyId) {
		this.customerSurveyId = customerSurveyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurveyList() {
		return surveyList;
	}

	public void setSurveyList(String surveyList) {
		this.surveyList = surveyList;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	
}
