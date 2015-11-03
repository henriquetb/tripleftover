package com.tripleftover.repository;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tripleftover.Offer;
import com.tripleftover.User;
import com.tripleftover.Util.CurrencyCode;


public interface OfferRepository extends JpaRepository<Offer, Long>{

	Offer findById(Long id);
	Collection<Offer> findByUserId(Long userId);
	
	Collection<Offer> findByUserName(String userName);
	Collection<Offer> findByHas(CurrencyCode has);
	Collection<Offer> findByWants(CurrencyCode wants);
	Collection<Offer> findByHasAndWants(CurrencyCode has, CurrencyCode wants);
	Collection<Offer> findByHasAndAmount(CurrencyCode code, BigDecimal amount);
	Collection<Offer> findByWantsAndAmount(CurrencyCode code, BigDecimal amount);
	Collection<Offer> findByHasAndWantsAndAmount(CurrencyCode has,
			CurrencyCode wants, BigDecimal amount);
}
