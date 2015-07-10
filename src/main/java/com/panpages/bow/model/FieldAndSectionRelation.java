package com.panpages.bow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Field_Section_Relation")
public class FieldAndSectionRelation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RELATION_ID", nullable = false)
	private int id;

	@Column(name = "FIELD_ID", nullable = false)
	private int fieldId;
	
	@Column(name = "SECTION_ID", nullable = false)
	private int sectionId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public int getSectionId() {
		return sectionId;
	}

	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}

}
