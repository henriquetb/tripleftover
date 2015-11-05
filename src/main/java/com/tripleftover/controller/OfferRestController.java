package com.tripleftover.controller;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tripleftover.Offer;
import com.tripleftover.User;
import com.tripleftover.Util.CurrencyCode;
import com.tripleftover.repository.OfferRepository;


@RestController
@RequestMapping("/offers")
class OfferRestController {

	@RequestMapping("")
	Collection<Offer> offers (){
		return this.offerRepository.findAll();
	}
	
	@RequestMapping("/{id}")
	Offer offerById (@PathVariable("id") Long id){
		return this.offerRepository.findById(id);
	}
	
	@RequestMapping("/user/{userId}")
	Collection<Offer> offerByUserId (@PathVariable("userId") Long userId){
		return this.offerRepository.findByUserId(userId);
	}
	

	/**
	 * returns offers where users HAVE hasCurrency. 
	 * */
	@RequestMapping("/has/{hasCurrency}")
	Collection<Offer> offersHasCurrency (@PathVariable("hasCurrency") String code){
		return this.offerRepository.findByHas(CurrencyCode.valueOf(code.toUpperCase()));
	}
	

	/**
	 * returns offers where users HAVE hasCurrency and amount. 
	 * */
	@RequestMapping("/has/{hasCurrency}/{amount}")
	Collection<Offer> offersHasCurrency (@PathVariable("hasCurrency") String code, @PathVariable("amount") BigDecimal amount){
		return this.offerRepository.findByHasAndAmount(CurrencyCode.valueOf(code.toUpperCase()), amount);
	}
	
	/**
	 * returns offers where users WANT hasCurrency. 
	 * */
	@RequestMapping("/wants/{wantsCurrency}")
	Collection<Offer> offersWantsCurrency (@PathVariable("wantsCurrency") String code){
		return this.offerRepository.findByWants(CurrencyCode.valueOf(code.toUpperCase()));
	}
	

	/**
	 * returns offers where users HAVE hasCurrency and amount. 
	 * */
	@RequestMapping("/wants/{wantsCurrency}/{amount}")
	Collection<Offer> offersWantsCurrency (@PathVariable("wantsCurrency") String code, @PathVariable("amount") BigDecimal amount){
		return this.offerRepository.findByWantsAndAmount(CurrencyCode.valueOf(code.toUpperCase()), amount);
	}
	
	/**
	 * returns offers of a given market. 
	 * */
	@RequestMapping("/{hasCurrency}/{wantsCurrency}")
	Collection<Offer> offersMarket (@PathVariable("hasCurrency") String codeHas, @PathVariable("wantsCurrency") String codeWants){
		return this.offerRepository.findByHasAndWants(CurrencyCode.valueOf(codeHas.toUpperCase()), CurrencyCode.valueOf(codeWants.toUpperCase()));
	}
	
	/**
	 * returns offers of a given market with a specific wants amount. 
	 * */
	@RequestMapping("/{hasCurrency}/{wantsCurrency}/{amount}")
	Collection<Offer> offersMarketAmount (@PathVariable("hasCurrency") String codeHas, 
			@PathVariable("wantsCurrency") String codeWants, 
			@PathVariable("amount") BigDecimal amount){
		return this.offerRepository.findByHasAndWantsAndAmount(CurrencyCode.valueOf(codeHas.toUpperCase()), CurrencyCode.valueOf(codeWants.toUpperCase()), amount);
	}


	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	Offer add (@RequestBody Offer input){
		return this.offerRepository.save(input);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	Offer add (@PathVariable("id") Long id, @RequestBody Offer input){
		Offer updatedOffer = this.offerRepository.findById(id);
		//updatedOffer.setName(input.getName());
		updatedOffer.setUser(input.getUser());
		updatedOffer.setHas(input.getHas());
		updatedOffer.setWants(input.getWants());
		updatedOffer.setAmount(input.getAmount());
		updatedOffer.setRate(input.getRate());
		updatedOffer.setDeal(input.getDeal());
		return this.offerRepository.save(updatedOffer);
	}
	

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	Offer delete (@PathVariable("id") Long id){
		Offer deleted = this.offerRepository.findById(id);
		if (deleted == null) return null;
		this.offerRepository.delete(id);
		return deleted;
	}
	
	@Autowired OfferRepository offerRepository; 
}
