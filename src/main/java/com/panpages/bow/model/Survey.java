package com.panpages.bow.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

@Entity
@Table(name="Survey")
public class Survey {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SURVEY_ID", nullable = false)
	private int id;

	@Column(name = "SURVEY_TEMPLATE_ID", nullable = false)
	private int surveyTemplateId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="SURVEY_TEMPLATE_ID", insertable=false, updatable=false)
	private SurveyTemplate surveyTemplate; 
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "[DESC]", nullable = true)
	private String desc;
	
	@Column(name = "STATUS", nullable = true)
	private String status;
	
	@Column(name = "STORAGE_NAME", nullable = false)
	private String storageName;
	
	@Column(name = "FULFILLED_DATE", nullable = false)
	@Type(type="timestamp")
	private Date fulfilledDate;
	
	@Column(name = "MADE_BY", nullable = false)
	private String madeBy;
	
	@ManyToMany()
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(
			name = "Section_Survey_Relation",
			joinColumns =  { @JoinColumn(name = "SURVEY_ID", referencedColumnName = "SURVEY_ID") }, 
			inverseJoinColumns = { @JoinColumn(name = "SECTION_ID", table = "Section", referencedColumnName = "SECTION_ID") })
	private List<Section> sections; 

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public SurveyTemplate getSurveyTemplate() {
		return surveyTemplate;
	}

	public void setSurveyTemplate(SurveyTemplate surveyTemplate) {
		this.surveyTemplate = surveyTemplate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStorageName() {
		return storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	public int getSurveyTemplateId() {
		return surveyTemplateId;
	}

	public void setSurveyTemplateId(int surveyTemplateId) {
		this.surveyTemplateId = surveyTemplateId;
	}

	public String getFulfilledDate() {
		String newFulfilledDateStr;
		
		DateFormat newDateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
		TimeZone zone = TimeZone.getTimeZone("GMT+08:00");
		newDateFormat.setTimeZone(zone);
		
		newFulfilledDateStr = newDateFormat.format(fulfilledDate);
		
		return newFulfilledDateStr;
	}
	
	public Date getDate(){
		return fulfilledDate;
	}
	
	public String getFulfilledDate(String timezone) {
		if (timezone == null || timezone.trim().equalsIgnoreCase("")) {
			return getFulfilledDate();
		}
		
		String newFulfilledDateStr;
		
		DateFormat newDateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
		TimeZone zone = TimeZone.getTimeZone(timezone);
		newDateFormat.setTimeZone(zone);
		
		newFulfilledDateStr = newDateFormat.format(fulfilledDate);
		
		return newFulfilledDateStr;
	}
	
	public void setFulfilledDate() {
		this.fulfilledDate = new Date();
	}

	public String getMadeBy() {
		return madeBy;
	}

	public void setMadeBy(String madeBy) {
		this.madeBy = madeBy;
	}
	
}
