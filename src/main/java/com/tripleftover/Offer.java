package com.tripleftover;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.tripleftover.Util.CurrencyCode;

@Entity
public class Offer {
	
	@Id @GeneratedValue
	private Long id;
	
	@OneToOne
	private User user;
	
	private CurrencyCode has;
	private CurrencyCode wants;
	private BigDecimal amount;
	private Double rate;
	
	@OneToOne
	private User deal;
	
	public Offer() {
	}
	public Offer(User user, CurrencyCode has, CurrencyCode wants,
			BigDecimal amount, Double rate) {
		super();
		this.user = user;
		this.has = has;
		this.wants = wants;
		this.amount = amount;
		this.rate = rate;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public CurrencyCode getHas() {
		return has;
	}
	public void setHas(CurrencyCode has) {
		this.has = has;
	}
	public CurrencyCode getWants() {
		return wants;
	}
	public void setWants(CurrencyCode wants) {
		this.wants = wants;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public User getDeal() {
		return deal;
	}
	public void setDeal(User deal) {
		this.deal = deal;
	}
	@Override
	public String toString() {
		return "Offer [id=" + id + ", user=" + user.getName() + ", has=" + has.toString()
				+ ", wants=" + wants.toString() + ", amount=" + amount + ", rate=" + rate
				+ "]";
	}
	
	
	
	
	
}