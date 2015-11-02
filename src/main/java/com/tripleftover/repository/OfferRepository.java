package com.tripleftover.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tripleftover.Offer;
import com.tripleftover.Util.CurrencyCode;


public interface OfferRepository extends JpaRepository<Offer, Long>{
	Collection<Offer> findByUserName(String userName);
	Collection<Offer> findByHas(CurrencyCode has);
	Collection<Offer> findByWants(CurrencyCode wants);
	Collection<Offer> findByHasAndWants(CurrencyCode has, CurrencyCode wants);
}
