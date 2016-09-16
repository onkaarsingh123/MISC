package com.urbanlife.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@Entity
@Table(name="businesstype")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BusinessType implements Serializable{
	@Id
    @Column(name="Id")
    @GeneratedValue
    private Integer id;
	
	@Column(name="Type")
	private String type;
	
	@ManyToOne
	@JoinColumn(name="Attr1",referencedColumnName="Id")
	private BusinessAttributes attr1;
	
	@ManyToOne
	@JoinColumn(name="Attr2",referencedColumnName="Id")
	private BusinessAttributes attr2;
	
	@Column(name="Attr3")
	private Integer attr3;
	
	@Column(name="Attr4")
	private Integer attr4;
	
	@Column(name="Attr5")
	private Integer attr5;
	
	@Column(name="Attr6")
	private Integer attr6;
	
	@Column(name="Attr7")
	private Integer attr7;
	
	@Column(name="Attr8")
	private Integer attr8;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BusinessAttributes getAttr1() {
		return attr1;
	}

	public void setAttr1(BusinessAttributes attr1) {
		this.attr1 = attr1;
	}

	public BusinessAttributes getAttr2() {
		return attr2;
	}

	public void setAttr2(BusinessAttributes attr2) {
		this.attr2 = attr2;
	}

	public Integer getAttr3() {
		return attr3;
	}

	public void setAttr3(Integer attr3) {
		this.attr3 = attr3;
	}

	public Integer getAttr4() {
		return attr4;
	}

	public void setAttr4(Integer attr4) {
		this.attr4 = attr4;
	}

	public Integer getAttr5() {
		return attr5;
	}

	public void setAttr5(Integer attr5) {
		this.attr5 = attr5;
	}

	public Integer getAttr6() {
		return attr6;
	}

	public void setAttr6(Integer attr6) {
		this.attr6 = attr6;
	}

	public Integer getAttr7() {
		return attr7;
	}

	public void setAttr7(Integer attr7) {
		this.attr7 = attr7;
	}

	public Integer getAttr8() {
		return attr8;
	}

	public void setAttr8(Integer attr8) {
		this.attr8 = attr8;
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
