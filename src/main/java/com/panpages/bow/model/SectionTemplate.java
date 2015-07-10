package com.panpages.bow.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Section_Template")
public class SectionTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SECTION_ID", nullable = false)
	private int id;

	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "PATH", nullable = true)
	private String path;
	
	@Column(name = "[DESC]", nullable = true)
	private String desc;
	
	@Column(name = "SLUG_NAME", nullable = false)
	private String slugName;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "Field_Section_Relation_In_Template",
			joinColumns =  { @JoinColumn(name = "SECTION_ID", referencedColumnName = "SECTION_ID") }, 
			inverseJoinColumns = { @JoinColumn(name = "FIELD_ID", table = "Field_Template", referencedColumnName = "FIELD_ID") })
	private List<FieldTemplate> fieldTemplates; 

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

	public List<FieldTemplate> getFieldTemplates() {
		return fieldTemplates;
	}

	public void setFieldTemplates(List<FieldTemplate> fieldTemplates) {
		this.fieldTemplates = fieldTemplates;
	}

	public String getSlugName() {
		return slugName;
	}

	public void setSlugName(String slugName) {
		this.slugName = slugName;
	}
}
