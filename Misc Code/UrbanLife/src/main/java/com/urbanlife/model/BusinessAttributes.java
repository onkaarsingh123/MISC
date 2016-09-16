package com.urbanlife.model;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name="businessattributes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BusinessAttributes implements Serializable{
	
	@Id
    @Column(name="Id")
    @GeneratedValue
    private Integer id;
	@Column(name="AttributeName")
	private String AttributeName;
	
	@Column(name="AttributeValue")
	private String AttributeValue;
	
	@Column(name="AttributeType")
	private String AttributeType;
	
	@Column(name="CreatedBy")
	private String createdBy;
	
	@Column(name="CreatedDate")
	private Date createdDate;
	
	@Column(name="ModifiedBy")
	private String modifiedBy;
	
	@Column(name="ModifiedDate")
	private Date modifiedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAttributeName() {
		return AttributeName;
	}

	public void setAttributeName(String attributeName) {
		AttributeName = attributeName;
	}

	public String getAttributeValue() {
		return AttributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		AttributeValue = attributeValue;
	}

	public String getAttributeType() {
		return AttributeType;
	}

	public void setAttributeType(String attributeType) {
		AttributeType = attributeType;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
}
