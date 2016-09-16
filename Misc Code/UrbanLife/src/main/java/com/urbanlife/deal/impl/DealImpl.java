package com.urbanlife.deal.impl;

import java.util.Date;

public class DealImpl {

	public int id = -1;

//	DayOfWeek dayOfWeek;
//	DealStatus dealStatus;
//	DealType dealType;
	public Date dealCreateDate = null;
	public Date expiresOn = null;
	public Date dealStartTime = null;
	public Date dealEndTime = null;
	public int discount = -1;
	public int minSeats = -1;
	public int maxSeats = -1;
	public String dealOffer = null;

	public DealImpl() {
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Deal [id=" + id + ", dealCreateDate=" + dealCreateDate
				+ ", expiresOn=" + expiresOn + ", dealStartTime="
				+ dealStartTime + ", dealEndTime=" + dealEndTime
				+ ", discount=" + discount + ", minSeats=" + minSeats
				+ ", maxSeats=" + maxSeats + ", dealOffer=" + dealOffer
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDealCreateDate() {
		return dealCreateDate;
	}

	public void setDealCreateDate(Date dealCreateDate) {
		this.dealCreateDate = dealCreateDate;
	}

	public Date getExpiresOn() {
		return expiresOn;
	}

	public void setExpiresOn(Date expiresOn) {
		this.expiresOn = expiresOn;
	}

	public Date getDealStartTime() {
		return dealStartTime;
	}

	public void setDealStartTime(Date dealStartTime) {
		this.dealStartTime = dealStartTime;
	}

	public Date getDealEndTime() {
		return dealEndTime;
	}

	public void setDealEndTime(Date dealEndTime) {
		this.dealEndTime = dealEndTime;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getMinSeats() {
		return minSeats;
	}

	public void setMinSeats(int minSeats) {
		this.minSeats = minSeats;
	}

	public int getMaxSeats() {
		return maxSeats;
	}

	public void setMaxSeats(int maxSeats) {
		this.maxSeats = maxSeats;
	}

	public String getDealOffer() {
		return dealOffer;
	}

	public void setDealOffer(String dealOffer) {
		this.dealOffer = dealOffer;
	}

}
