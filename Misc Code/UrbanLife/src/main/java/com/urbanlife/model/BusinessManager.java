package com.urbanlife.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


import com.urbanlife.enums.RegType;

@Entity
@Table(name="businessmanager")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BusinessManager implements Serializable{

	@Id
    @Column(name="Id")
    @GeneratedValue
    private Integer id;

	@Column(name="emailId")
	private String email;
	
	@Column(name="regType",columnDefinition = "enum")
	@Enumerated(EnumType.STRING)
	public RegType regType;
	
	@Column(name="authToken")
	private String authToken;
	
	@Column(name="authTokenExpires")
	private long authTokenExpires;
	
	@ManyToOne
	@JoinColumn(name="BusinessId",referencedColumnName="Id")
	public Business business;
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public RegType getRegType() {
		return regType;
	}

	public void setRegType(RegType regType) {
		this.regType = regType;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public long getAuthTokenExpires() {
		return authTokenExpires;
	}

	public void setAuthTokenExpires(long authTokenExpires) {
		this.authTokenExpires = authTokenExpires;
	}



	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
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

	@Override
	public String toString() {
		return "BusinessManager [id=" + id + ", email=" + email + ", regType="
				+ regType + ", authToken=" + authToken + ", authTokenExpires="
				+ authTokenExpires + ", businessId=" + business
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}
	
	
}
