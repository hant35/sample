package com.panpages.bow.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Field")
public class Field {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FIELD_ID", nullable = false)
	private int id;
	
	@Column(name = "FIELD_TEMPLATE_ID", nullable = false)
	private int fieldTemplateId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="FIELD_TEMPLATE_ID", insertable=false, updatable=false)
	private FieldTemplate fieldTemplate; 

	@Column(name = "[VALUE]", nullable = false)
	private String value;
	
	@Column(name = "[DESC]", nullable = true)
	private String desc;
	
	@Column(name = "NAME", nullable = true)
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public FieldTemplate getFieldTemplate() {
		return fieldTemplate;
	}

	public void setFieldTemplate(FieldTemplate fieldTemplate) {
		this.fieldTemplate = fieldTemplate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFieldTemplateId() {
		return fieldTemplateId;
	}

	public void setFieldTemplateId(int fieldTemplateId) {
		this.fieldTemplateId = fieldTemplateId;
	}
}
