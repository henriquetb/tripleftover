package com.tripleftover.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripleftover.Offer;
import com.tripleftover.Util.CurrencyCode;
import com.tripleftover.repository.OfferRepository;


@RestController
@RequestMapping("/offers")
class OfferRestController {

	@RequestMapping("")
	Collection<Offer> offers (){
		return this.offerRepository.findAll();
	}
	

	/**
	 * returns offers where users HAVE hasCurrency. 
	 * */
	@RequestMapping("/has/{hasCurrency}")
	Collection<Offer> offersHasCurrency (@PathVariable("hasCurrency") String code){
		return this.offerRepository.findByHas(CurrencyCode.valueOf(code.toUpperCase()));
	}
	
	/**
	 * returns offers where users WANT hasCurrency. 
	 * */
	@RequestMapping("/wants/{wantsCurrency}")
	Collection<Offer> offersWantsCurrency (@PathVariable("wantsCurrency") String code){
		return this.offerRepository.findByWants(CurrencyCode.valueOf(code.toUpperCase()));
	}
	
	/**
	 * returns offers of a given market. 
	 * */
	@RequestMapping("/{hasCurrency}/{wantsCurrency}")
	Collection<Offer> offersMarket (@PathVariable("hasCurrency") String codeHas, @PathVariable("wantsCurrency") String codeWants){
		return this.offerRepository.findByHasAndWants(CurrencyCode.valueOf(codeHas.toUpperCase()), CurrencyCode.valueOf(codeWants.toUpperCase()));
	}
	
	@Autowired OfferRepository offerRepository; 
}
