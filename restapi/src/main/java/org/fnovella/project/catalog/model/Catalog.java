package org.fnovella.project.catalog.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.fnovella.project.utility.APIUtility;
import org.hibernate.validator.constraints.Length;

@Entity
public class Catalog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Length(max=50)
	private String name;
	@Length(max=50)
	private String type;
	@Length(max=50)
	private String category;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Catalog() {
		super();
	}
	
	public ArrayList<String> validate() {
		ArrayList<String> errors = new ArrayList<String>();
		if (!APIUtility.isNotNullOrEmpty(this.name)) errors.add("Name is required");
		if (!APIUtility.isNotNullOrEmpty(this.type)) errors.add("Type is required");
		if (!APIUtility.isNotNullOrEmpty(this.category)) errors.add("Category is required");
		return errors;
	}
	
	public void setUpdateFields(Catalog catalog) {
		if (APIUtility.isNotNullOrEmpty(catalog.name)) this.name = catalog.name;
		if (APIUtility.isNotNullOrEmpty(catalog.type)) this.type = catalog.type;
		if (APIUtility.isNotNullOrEmpty(catalog.category)) this.category = catalog.category;
	}
	
}
