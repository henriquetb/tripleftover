package com.tripleftover.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripleftover.Currency;
import com.tripleftover.Offer;
import com.tripleftover.repository.CurrencyRepository;
import com.tripleftover.repository.OfferRepository;


@RestController
class CurrencyRestController {
	
	@RequestMapping("/currencies")
	Collection<Currency> currencies (){
		return this.currencyRepository.findAll();
	}
	
	@Autowired CurrencyRepository currencyRepository; 
}
