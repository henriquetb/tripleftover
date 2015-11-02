package com.tripleftover;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.tripleftover.Util.CurrencyCode;


@Entity
public class Currency {
	
	@Id
	private CurrencyCode id;
	private String name;
	private String region;
	
	public Currency() {
	}
	public Currency(CurrencyCode id,  String name, String region) {
		super();
		this.id = id;
		this.name = name;
		this.region = region;
		
	}
	
	public CurrencyCode getId() {
		return id;
	}
	public void setId(CurrencyCode id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "Currency [id=" + id + ", name=" + name + ", region=" + region
				+ "]";
	}
	
}