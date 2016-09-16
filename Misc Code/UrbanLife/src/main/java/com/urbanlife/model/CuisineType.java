package com.urbanlife.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name="cuisinetype")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CuisineType {

	@Id
    @Column(name="Id")
    @GeneratedValue
    private Integer id;
	

	@Column(name="Name")
	private String name;


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
	
	
}
