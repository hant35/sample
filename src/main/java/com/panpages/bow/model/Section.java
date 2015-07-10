package com.panpages.bow.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Section")
public class Section {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SECTION_ID", nullable = false)
	private int id;

	@Column(name = "SECTION_TEMPLATE_ID", nullable = false)
	private int sectionTemplateId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="SECTION_TEMPLATE_ID", insertable=false, updatable=false)
	private SectionTemplate sectionTemplate; 
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "[DESC]", nullable = true)
	private String desc;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "Field_Section_Relation",
			joinColumns =  { @JoinColumn(name = "SECTION_ID", referencedColumnName = "SECTION_ID") }, 
			inverseJoinColumns = { @JoinColumn(name = "FIELD_ID", table = "Field", referencedColumnName = "FIELD_ID") })
	private List<Field> fields; 

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

	public int getSectionTemplateId() {
		return sectionTemplateId;
	}

	public void setSectionTemplateId(int sectionTemplateId) {
		this.sectionTemplateId = sectionTemplateId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public SectionTemplate getSectionTemplate() {
		return sectionTemplate;
	}

	public void setSectionTemplate(SectionTemplate sectionTemplate) {
		this.sectionTemplate = sectionTemplate;
	}
	
	public Field findFieldWithFieldTemplateSlugName(String slugName) {
		if (slugName == null || slugName.trim().equals("") || getFields().size() == 0) {
			return null;
		}
		
		for (Field field:getFields()) {
			FieldTemplate fieldTempl = field.getFieldTemplate();
			String templSlugName = fieldTempl.getSlugName();
			if (slugName.equalsIgnoreCase(templSlugName)) {
				return field;
			}
		}
		
		return null;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
}
