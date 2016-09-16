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
@Table(name="account")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Account implements Serializable {
	

		private static final long serialVersionUID = 1L;

		@Id
	    @Column(name="Id")
	    @GeneratedValue
	    private Integer id;
		
		@Column(name="businessOwnerId")
		private Integer businessOwnerId;
		
		@Column(name="StripeId")
		private String stripeId;
		
		@Column(name="CardId")
		private String cardId;
		
		@Column(name="AccountState")
		private String accountState;
		
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



		public Integer getBusinessOwnerId() {
			return businessOwnerId;
		}

		public void setBusinessOwnerId(Integer businessOwnerId) {
			this.businessOwnerId = businessOwnerId;
		}

		public String getStripeId() {
			return stripeId;
		}

		public void setStripeId(String stripeId) {
			this.stripeId = stripeId;
		}

		public String getCardId() {
			return cardId;
		}

		public void setCardId(String cardId) {
			this.cardId = cardId;
		}

		public String getAccountState() {
			return accountState;
		}

		public void setAccountState(String accountState) {
			this.accountState = accountState;
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

		public void setModifiedDate(Date modifiedDate) {
			this.modifiedDate = modifiedDate;
		}

		public Date getModifiedDate() {
			return modifiedDate;
		}

	

		
		

}
	
