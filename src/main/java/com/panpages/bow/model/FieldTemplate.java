package com.panpages.bow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Field_Template")
public class FieldTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FIELD_ID", nullable = false)
	private int id;

	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "[TYPE]", nullable = false)
	private String type;
	
	@Column(name = "[DESC]", nullable = true)
	private String desc;
	
	@Column(name = "SLUG_NAME", nullable = false)
	private String slugName;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSlugName() {
		return slugName;
	}

	public void setSlugName(String slugName) {
		this.slugName = slugName;
	}
}
