package com.onkaar.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Expenditure implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4617968345283073900L;
	private String expenditureId;
	private Group group;
	private User user;
	private String spendingName;
	private float spendingAmount;
	public Expenditure() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Expenditure(String expenditureId, Group group, User user,
			String spendingName, float spendingAmount) {
		super();
		this.expenditureId = expenditureId;
		this.group = group;
		this.user = user;
		this.spendingName = spendingName;
		this.spendingAmount = spendingAmount;
	}
	public String getExpenditureId() {
		return expenditureId;
	}
	public void setExpenditureId(String expenditureId) {
		this.expenditureId = expenditureId;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getSpendingName() {
		return spendingName;
	}
	public void setSpendingName(String spendingName) {
		this.spendingName = spendingName;
	}
	public float getSpendingAmount() {
		return spendingAmount;
	}
	public void setSpendingAmount(float spendingAmount) {
		this.spendingAmount = spendingAmount;
	}
	@Override
	public String toString() {
		return "Expenditure [expenditureId=" + expenditureId + ", group="
				+ group + ", user=" + user + ", spendingName=" + spendingName
				+ ", spendingAmount=" + spendingAmount + "]";
	}
	
	
	
	
	

}
