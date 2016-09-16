package com.onkaar.bean;

public class NormalizedData {

	private User user;
	private Group group;
	private float balance;
	public NormalizedData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NormalizedData(User user, Group group, float balance) {
		super();
		this.user = user;
		this.group = group;
		this.balance = balance;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "NormailzedData [user=" + user + ", group=" + group
				+ ", balance=" + balance + "]\n";
	}
	
}
