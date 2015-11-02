package com.tripleftover.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tripleftover.Currency;


public interface CurrencyRepository extends JpaRepository<Currency, Long>{
	Collection<Currency> findByRegion(String region);
}
