package com.panpages.bow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Section_Survey_Relation")
public class SectionAndSurveyRelation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RELATION_ID", nullable = false)
	private int id;

	@Column(name = "SECTION_ID", nullable = false)
	private int sectionId;
	
	@Column(name = "SURVEY_ID", nullable = false)
	private int surveyId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSectionId() {
		return sectionId;
	}

	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}

	public int getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}

}
