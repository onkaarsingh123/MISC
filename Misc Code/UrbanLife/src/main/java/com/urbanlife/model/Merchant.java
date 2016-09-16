package com.urbanlife.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.mysql.jdbc.Blob;


@Entity
@Table(name="businessowner")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region="businessowner")
public class Merchant implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="Id")
    @GeneratedValue
    private Integer id;
	

	@Column(name = "AccountId")
	private Integer accountId;
	
	@Column(name = "emailId")
	private String emailId;

	@Column(name = "IsActive")
	private Boolean isActive;
	
	
	@Column(name = "regType")
	private String regType;
	
	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
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

	@Column(name = "authToken")
	private String authToken;

	@Column(name = "authTokenExpires")
	private long authTokenExpires;
	
	@Column(name = "CreatedBy")
	private String createdBy;

	@Column(name = "CreatedDate")
	private Date createdDate;

	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Column(name = "ModifiedDate")
	private Date modifiedDate;

	


	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}



}
	
	
   