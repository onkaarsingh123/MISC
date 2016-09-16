package com.urbanlife.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name="city")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class City implements Serializable{

	@Id
    @Column(name="Id")
    @GeneratedValue
    private Integer id;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="CountryCode")
	private String countryCode;
	
	@Column(name="State")
	private String state;

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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + ", countryCode="
				+ countryCode + ", state=" + state + "]";
	}
	
	
}
