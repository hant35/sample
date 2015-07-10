package com.panpages.bow.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="Survey_Template")
public class SurveyTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SURVEY_ID", nullable = false)
	private int id;

	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "PATH", nullable = true)
	private String path;
	
	@Column(name = "[DESC]", nullable = true)
	private String desc;
	
	@Column(name = "SLUG_NAME", nullable = false)
	private String slugName;
	
	@Autowired
	@ManyToMany()
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(
			name = "Section_Survey_Relation_In_Template",
			joinColumns =  { @JoinColumn(name = "SURVEY_ID", referencedColumnName = "SURVEY_ID") }, 
			inverseJoinColumns = { @JoinColumn(name = "SECTION_ID", table = "Section_Template", referencedColumnName = "SECTION_ID") })
	private List<SectionTemplate> sectionTemplates; 

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<SectionTemplate> getSectionTemplates() {
		return sectionTemplates;
	}

	public void setSectionTemplates(List<SectionTemplate> sectionTemplates) {
		this.sectionTemplates = sectionTemplates;
	}

	public String getSlugName() {
		return slugName;
	}

	public void setSlugName(String slugName) {
		this.slugName = slugName;
	}
}
