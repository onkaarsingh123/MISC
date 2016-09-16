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
import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name="business")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Business implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="Id")
    @GeneratedValue
    private Integer id;

	
	@Column(name="Name")
	private String name;
	

	@Column(name="ContactNumber")
	private String contactNumber;
	

	
	@Column(name="Address")
	private String address;
	
	@Column(name="City")
	private String city;
	

	
	@Column(name="State")
	private String state; 
	
	@Column(name="ZipCode")
	private int zipCode;
	
	@Column(name="PhotoURL")
	
	private String photoUrl;
	
	@Column(name="Photo")
	private byte[] photo;
	
	@Column(name="Country")
	private String country; 
	

	@Column(name="EmailId")
	private String emailId; 
	
	
	@Column(name="MinSeats")
	private String minSeats; 
	

	@Column(name="MaxSeats")
	private String maxSeats; 

	@Column(name="BusinessDetails")
	private String businessDetails; 
	
	@ManyToOne
	@JoinColumn(name="BusinessType",referencedColumnName="Id")
	private BusinessType businessType; 
	
	@Column(name="BusinessAccount")
	private String businessAccount;
	
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getBusinessDetails() {
		return businessDetails;
	}

	public void setBusinessDetails(String businessDetails) {
		this.businessDetails = businessDetails;
	}

	@Column(name="BookingAgreedTime")
	private String bookingAgreedTime; 
	
	@Column(name="BookingGraceTime")
	private String bookingGraceTime;
	
	@Column(name="TripAdvisorId")
	private int tripAdvisorId;
	
	@Column(name="BusinessHours")
	private String businessHours;
	
	
	@Column(name="CreatedBy")
	private String createdBy;
	
	@Column(name="CreatedDate")
	private Date createdDate;
	
	@Column(name="ModifiedBy")
	private String modifiedBy;
	
	@Column(name="ModifiedDate")
	private Date modifiedDate;
	

	
	@ManyToOne
	@JoinColumn(name="AccountId",referencedColumnName="Id")
	private Account account;
	

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

	public String getTelephone() {
		return contactNumber;
	}

	public void setTelephone(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	

	
	public String getMinSeats() {
		return minSeats;
	}

	public void setMinSeats(String minSeats) {
		this.minSeats = minSeats;
	}

	public String getMaxSeats() {
		return maxSeats;
	}

	public void setMaxSeats(String maxSeats) {
		this.maxSeats = maxSeats;
	}

	public BusinessType getBusinessTypeId() {
		return businessType;
	}

	public void setBusinessTypeId(BusinessType businessTypeId) {
		this.businessType = businessTypeId;
	}



	public String getBusinessAccount() {
		return businessAccount;
	}

	public void setBusinessAccount(String businessAccount) {
		this.businessAccount = businessAccount;
	}

	

	public String getBookingAgreedTime() {
		return bookingAgreedTime;
	}

	public void setBookingAgreedTime(String bookingAgreedTime) {
		this.bookingAgreedTime = bookingAgreedTime;
	}

	public String getBookingGraceTime() {
		return bookingGraceTime;
	}

	public void setBookingGraceTime(String bookingGraceTime) {
		this.bookingGraceTime = bookingGraceTime;
	}

	public int getTripAdvisorId() {
		return tripAdvisorId;
	}

	public void setTripAdvisorId(int tripAdvisorId) {
		this.tripAdvisorId = tripAdvisorId;
	}

	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
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

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	


	

}
