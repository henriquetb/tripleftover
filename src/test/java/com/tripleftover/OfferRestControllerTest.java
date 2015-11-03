package com.tripleftover;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.RequestBuilder;

import com.tripleftover.Util.CurrencyCode;
import com.tripleftover.repository.OfferRepository;
import com.tripleftover.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


/**
 * This test class will test the contoller by mocking the api endpoint responses.
 * No need to have a functional server running. 
 * */
public class OfferRestControllerTest extends RestControllersTest{
	
	private String baseUrl = "/offers";
	
	private List<Offer> offerList = new ArrayList<Offer>();
	private List<User> userList = new ArrayList<User>();
	
	@Before
	public void setUp(){
		super.setUp();

		userList.add(userRepository.save(new User("Henrique")));
		userList.add(userRepository.save(new User("John")));
		userList.add(userRepository.save(new User("Bilbo")));
		
		
		offerList.add(offerRepository.save(new Offer(userList.get(0), CurrencyCode.AUD, CurrencyCode.EUR, new BigDecimal(100), 1.4)));
		offerList.add(offerRepository.save(new Offer(userList.get(1), CurrencyCode.AUD, CurrencyCode.EUR, new BigDecimal(200), 1.41)));
		offerList.add(offerRepository.save(new Offer(userList.get(0), CurrencyCode.USD, CurrencyCode.EUR, new BigDecimal(1000), 0.8)));
		offerList.add(offerRepository.save(new Offer(userList.get(1), CurrencyCode.USD, CurrencyCode.EUR, new BigDecimal(2000), 0.79)));
	}
	
	

	@Test
	public void findAll_returns4Offers() throws Exception {
		mockMvc.perform(getOffer(null, null, null, null, null, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(4)));
	}
	
	
	
	
	
	@Test
	public void findById_NonExistingOfferId_returnsEmptyString() throws Exception {
		mockMvc.perform(getOffer(Long.MAX_VALUE, null, null, null, null, null))
				.andExpect(status().isOk())
				.andExpect(content().string(""));
				//.andDo(print());
	}
	
	
	@Test
	public void findById_ExistingOfferId_returnsOfferAttributes() throws Exception {
		mockMvc.perform(getOffer(offerList.get(0).getId(), null, null, null, null, null))
				.andExpect(status().isOk())
				//.andDo(print())
				.andExpect(jsonPath("$.id", Matchers.equalTo(offerList.get(0).getId().intValue())))
				.andExpect(jsonPath("$.user.id", Matchers.equalTo(offerList.get(0).getUser().getId().intValue())))
				.andExpect(jsonPath("$.has", Matchers.equalTo(offerList.get(0).getHas().toString())))
				.andExpect(jsonPath("$.wants", Matchers.equalTo(offerList.get(0).getWants().toString())))
				.andExpect(jsonPath("$.amount", Matchers.equalTo(offerList.get(0).getAmount().doubleValue())))
				.andExpect(jsonPath("$.rate", Matchers.equalTo(offerList.get(0).getRate().doubleValue())));
	}
	
	

	
	@Test
	public void findByUserId_NonExistingUser_returnsEmptyArray() throws Exception {
		mockMvc.perform(getOffer(null, Long.MAX_VALUE, null, null, null, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(0)));
				//.andDo(print());
	}
	
	@Test
	public void findByUserId_UserHasNoOffers_returnsEmptyArray() throws Exception {
		mockMvc.perform(getOffer(null, userList.get(2).getId(), null, null, null, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(0)));
				//.andDo(print());
	}
	
	@Test
	public void findByUserId_ExistingUser_returns2OfferAttributes() throws Exception {
		mockMvc.perform(getOffer(null, userList.get(0).getId(), null, null, null, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(2)))
		
				.andExpect(jsonPath("$[0].id", Matchers.equalTo(offerList.get(0).getId().intValue())))
				.andExpect(jsonPath("$[0].user.id", Matchers.equalTo(offerList.get(0).getUser().getId().intValue())))
				.andExpect(jsonPath("$[0].has", Matchers.equalTo(offerList.get(0).getHas().toString())))
				.andExpect(jsonPath("$[0].wants", Matchers.equalTo(offerList.get(0).getWants().toString())))
				.andExpect(jsonPath("$[0].amount", Matchers.equalTo(offerList.get(0).getAmount().doubleValue())))
				.andExpect(jsonPath("$[0].rate", Matchers.equalTo(offerList.get(0).getRate().doubleValue())))
				
				.andExpect(jsonPath("$[1].id", Matchers.equalTo(offerList.get(2).getId().intValue())))
				.andExpect(jsonPath("$[1].user.id", Matchers.equalTo(offerList.get(2).getUser().getId().intValue())))
				.andExpect(jsonPath("$[1].has", Matchers.equalTo(offerList.get(2).getHas().toString())))
				.andExpect(jsonPath("$[1].wants", Matchers.equalTo(offerList.get(2).getWants().toString())))
				.andExpect(jsonPath("$[1].amount", Matchers.equalTo(offerList.get(2).getAmount().doubleValue())))
				.andExpect(jsonPath("$[1].rate", Matchers.equalTo(offerList.get(2).getRate().doubleValue())));
	}
	
	
	

	@Test
	public void findByHas_NoOffersWithHas_returnsEmptyArray() throws Exception {
		mockMvc.perform(getOffer(null, null, CurrencyCode.BRL, null, null, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(0)));
				//.andDo(print());
	}
	
	@Test
	public void findByHas_ExistingOffersWithHas_returns2OfferAttributes() throws Exception {
		mockMvc.perform(getOffer(null, null, CurrencyCode.AUD, null, null, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(2)))
		
				.andExpect(jsonPath("$[0].id", Matchers.equalTo(offerList.get(0).getId().intValue())))
				.andExpect(jsonPath("$[0].user.id", Matchers.equalTo(offerList.get(0).getUser().getId().intValue())))
				.andExpect(jsonPath("$[0].has", Matchers.equalTo(offerList.get(0).getHas().toString())))
				.andExpect(jsonPath("$[0].wants", Matchers.equalTo(offerList.get(0).getWants().toString())))
				.andExpect(jsonPath("$[0].amount", Matchers.equalTo(offerList.get(0).getAmount().doubleValue())))
				.andExpect(jsonPath("$[0].rate", Matchers.equalTo(offerList.get(0).getRate().doubleValue())))
				
				.andExpect(jsonPath("$[1].id", Matchers.equalTo(offerList.get(1).getId().intValue())))
				.andExpect(jsonPath("$[1].user.id", Matchers.equalTo(offerList.get(1).getUser().getId().intValue())))
				.andExpect(jsonPath("$[1].has", Matchers.equalTo(offerList.get(1).getHas().toString())))
				.andExpect(jsonPath("$[1].wants", Matchers.equalTo(offerList.get(1).getWants().toString())))
				.andExpect(jsonPath("$[1].amount", Matchers.equalTo(offerList.get(1).getAmount().doubleValue())))
				.andExpect(jsonPath("$[1].rate", Matchers.equalTo(offerList.get(1).getRate().doubleValue())));
	}
	
	
	

	@Test
	public void findByWants_NoOffersWithWants_returnsEmptyArray() throws Exception {
		mockMvc.perform(getOffer(null, null, null, null,  CurrencyCode.BRL, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(0)));
				//.andDo(print());
	}
	
	@Test
	public void findByWants_ExistingOfferWithWants_returns4OfferAttributes() throws Exception {
		mockMvc.perform(getOffer(null, null, null, null,  CurrencyCode.EUR, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(4)))
		
				.andExpect(jsonPath("$[0].id", Matchers.equalTo(offerList.get(0).getId().intValue())))
				.andExpect(jsonPath("$[0].user.id", Matchers.equalTo(offerList.get(0).getUser().getId().intValue())))
				.andExpect(jsonPath("$[0].has", Matchers.equalTo(offerList.get(0).getHas().toString())))
				.andExpect(jsonPath("$[0].wants", Matchers.equalTo(offerList.get(0).getWants().toString())))
				.andExpect(jsonPath("$[0].amount", Matchers.equalTo(offerList.get(0).getAmount().doubleValue())))
				.andExpect(jsonPath("$[0].rate", Matchers.equalTo(offerList.get(0).getRate().doubleValue())))
				
				.andExpect(jsonPath("$[1].id", Matchers.equalTo(offerList.get(1).getId().intValue())))
				.andExpect(jsonPath("$[1].user.id", Matchers.equalTo(offerList.get(1).getUser().getId().intValue())))
				.andExpect(jsonPath("$[1].has", Matchers.equalTo(offerList.get(1).getHas().toString())))
				.andExpect(jsonPath("$[1].wants", Matchers.equalTo(offerList.get(1).getWants().toString())))
				.andExpect(jsonPath("$[1].amount", Matchers.equalTo(offerList.get(1).getAmount().doubleValue())))
				.andExpect(jsonPath("$[1].rate", Matchers.equalTo(offerList.get(1).getRate().doubleValue())));
	}
	
	
	
	
	@Test
	public void findByHasAndWants_NoOffersWithHasAndWants_returnsEmptyArray() throws Exception {
		mockMvc.perform(getOffer(null, null, null, null,  CurrencyCode.BRL, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(0)));
				//.andDo(print());
	}
	
	@Test
	public void findByHasAndWants_ExistingOffersWithHasAndWants_returns2OfferAttributes() throws Exception {
		mockMvc.perform(getOffer(null, null, CurrencyCode.AUD, null,  CurrencyCode.EUR, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(2)))
		
				.andExpect(jsonPath("$[0].id", Matchers.equalTo(offerList.get(0).getId().intValue())))
				.andExpect(jsonPath("$[0].user.id", Matchers.equalTo(offerList.get(0).getUser().getId().intValue())))
				.andExpect(jsonPath("$[0].has", Matchers.equalTo(offerList.get(0).getHas().toString())))
				.andExpect(jsonPath("$[0].wants", Matchers.equalTo(offerList.get(0).getWants().toString())))
				.andExpect(jsonPath("$[0].amount", Matchers.equalTo(offerList.get(0).getAmount().doubleValue())))
				.andExpect(jsonPath("$[0].rate", Matchers.equalTo(offerList.get(0).getRate().doubleValue())))
				
				.andExpect(jsonPath("$[1].id", Matchers.equalTo(offerList.get(1).getId().intValue())))
				.andExpect(jsonPath("$[1].user.id", Matchers.equalTo(offerList.get(1).getUser().getId().intValue())))
				.andExpect(jsonPath("$[1].has", Matchers.equalTo(offerList.get(1).getHas().toString())))
				.andExpect(jsonPath("$[1].wants", Matchers.equalTo(offerList.get(1).getWants().toString())))
				.andExpect(jsonPath("$[1].amount", Matchers.equalTo(offerList.get(1).getAmount().doubleValue())))
				.andExpect(jsonPath("$[1].rate", Matchers.equalTo(offerList.get(1).getRate().doubleValue())));
	}
	
	
	
	
	@Test
	public void findByHasAndAmount_NoOffersWithHasAndAmount_returnsEmptyArray() throws Exception {
		mockMvc.perform(getOffer(null, null, CurrencyCode.AUD, new BigDecimal(123), null, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(0)));
				//.andDo(print());
	}
	
	@Test
	public void findByHasAndAmount_ExistingOffersWithHasAndAmount_returns1OfferAttributes() throws Exception {
		mockMvc.perform(getOffer(null, null, CurrencyCode.AUD, new BigDecimal(100), null, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
		
				.andExpect(jsonPath("$[0].id", Matchers.equalTo(offerList.get(0).getId().intValue())))
				.andExpect(jsonPath("$[0].user.id", Matchers.equalTo(offerList.get(0).getUser().getId().intValue())))
				.andExpect(jsonPath("$[0].has", Matchers.equalTo(offerList.get(0).getHas().toString())))
				.andExpect(jsonPath("$[0].wants", Matchers.equalTo(offerList.get(0).getWants().toString())))
				.andExpect(jsonPath("$[0].amount", Matchers.equalTo(offerList.get(0).getAmount().doubleValue())))
				.andExpect(jsonPath("$[0].rate", Matchers.equalTo(offerList.get(0).getRate().doubleValue())));
	}
	
	
	
	
	@Test
	public void findByWantsAndAmount_NoOffersWithWantsAndAmount_returnsEmptyArray() throws Exception {
		mockMvc.perform(getOffer(null, null, null, null, CurrencyCode.AUD, new BigDecimal(123)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(0)));
				//.andDo(print());
	}
	
	@Test
	public void findByWantsAndAmount_ExistingOffersWithWantsAndAmount_returns1OfferAttributes() throws Exception {
		mockMvc.perform(getOffer(null, null, null, null, CurrencyCode.EUR, new BigDecimal(200)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
		
				.andExpect(jsonPath("$[0].id", Matchers.equalTo(offerList.get(1).getId().intValue())))
				.andExpect(jsonPath("$[0].user.id", Matchers.equalTo(offerList.get(1).getUser().getId().intValue())))
				.andExpect(jsonPath("$[0].has", Matchers.equalTo(offerList.get(1).getHas().toString())))
				.andExpect(jsonPath("$[0].wants", Matchers.equalTo(offerList.get(1).getWants().toString())))
				.andExpect(jsonPath("$[0].amount", Matchers.equalTo(offerList.get(1).getAmount().doubleValue())))
				.andExpect(jsonPath("$[0].rate", Matchers.equalTo(offerList.get(1).getRate().doubleValue())));
	}
	
	
	
	
	@Test
	public void findByHasAndWantsAndAmount_NoOffersWithHasWantsAndAmount_returnsEmptyArray() throws Exception {
		mockMvc.perform(getOffer(null, null, CurrencyCode.AUD, null, CurrencyCode.EUR, new BigDecimal(123)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(0)));
				//.andDo(print());
	}
	
	@Test
	public void findByHasWantsAndAmount_ExistingOffersWithHasWantsAndAmount_returns1OfferAttributes() throws Exception {
		mockMvc.perform(getOffer(null, null, CurrencyCode.AUD, null, CurrencyCode.EUR, new BigDecimal(200)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
		
				.andExpect(jsonPath("$[0].id", Matchers.equalTo(offerList.get(1).getId().intValue())))
				.andExpect(jsonPath("$[0].user.id", Matchers.equalTo(offerList.get(1).getUser().getId().intValue())))
				.andExpect(jsonPath("$[0].has", Matchers.equalTo(offerList.get(1).getHas().toString())))
				.andExpect(jsonPath("$[0].wants", Matchers.equalTo(offerList.get(1).getWants().toString())))
				.andExpect(jsonPath("$[0].amount", Matchers.equalTo(offerList.get(1).getAmount().doubleValue())))
				.andExpect(jsonPath("$[0].rate", Matchers.equalTo(offerList.get(1).getRate().doubleValue())));
	}

	
	@Test
	public void add_returnsNewOfferAttributes_AndHas5Entries() throws Exception {
		Offer newOffer = new Offer(userList.get(0), CurrencyCode.BRL, CurrencyCode.USD, new BigDecimal(500), 2.8);
		mockMvc.perform(saveOffer(newOffer, null))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value( Matchers.isA(Integer.class)))
				.andExpect(jsonPath("$.user.id", Matchers.equalTo(newOffer.getUser().getId().intValue())))
				.andExpect(jsonPath("$.has", Matchers.equalTo(newOffer.getHas().toString())))
				.andExpect(jsonPath("$.wants", Matchers.equalTo(newOffer.getWants().toString())))
				.andExpect(jsonPath("$.amount", Matchers.equalTo(newOffer.getAmount().intValue())))
				//TODO the BigDecimal attributes have the potential to fail this test 
				.andExpect(jsonPath("$.rate", Matchers.equalTo(newOffer.getRate().doubleValue())));

		mockMvc.perform(getOffer(null, null, null, null, null, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(5)));
	}
	
	@Test
	public void update_returnsOfferWithNewAttributes_AndHas4Entries() throws Exception {
		Offer newOffer = new Offer(userList.get(0), CurrencyCode.BRL, CurrencyCode.USD, new BigDecimal(500), 2.8);
		newOffer.setId(offerList.get(0).getId());
		
		//changes the values 
		mockMvc.perform(saveOffer(newOffer, newOffer.getId()))
				.andExpect(jsonPath("$.id", Matchers.equalTo( newOffer.getId().intValue() )))
				.andExpect(jsonPath("$.user.id", Matchers.equalTo(newOffer.getUser().getId().intValue())))
				.andExpect(jsonPath("$.has", Matchers.equalTo(newOffer.getHas().toString())))
				.andExpect(jsonPath("$.wants", Matchers.equalTo(newOffer.getWants().toString())))
				.andExpect(jsonPath("$.amount", Matchers.equalTo(newOffer.getAmount().intValue())))
				.andExpect(jsonPath("$.rate", Matchers.equalTo(newOffer.getRate().doubleValue())));
		

		mockMvc.perform(getOffer(null, null, null, null, null, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(4)))
				.andExpect(jsonPath("$[0].id", Matchers.equalTo( newOffer.getId().intValue() )))
				.andExpect(jsonPath("$[0].user.id", Matchers.equalTo(newOffer.getUser().getId().intValue())))
				.andExpect(jsonPath("$[0].has", Matchers.equalTo(newOffer.getHas().toString())))
				.andExpect(jsonPath("$[0].wants", Matchers.equalTo(newOffer.getWants().toString())))
				.andExpect(jsonPath("$[0].amount", Matchers.equalTo(newOffer.getAmount().doubleValue())))
				.andExpect(jsonPath("$[0].rate", Matchers.equalTo(newOffer.getRate().doubleValue())));;
	}


	@Test
	public void delete_NonExistingOfferId_returnsEmptyString_andHas4Entries() throws Exception {
		mockMvc.perform(delete(this.baseUrl+"/"+Long.MAX_VALUE)
				.contentType(contentType))
				.andExpect(status().isOk())
				.andExpect(content().string(""));
				//.andDo(print());
		
		mockMvc.perform(getOffer(null, null, null, null, null, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(4)));
	}


	@Test
	public void delete_ExistingOfferId_returnsDeletedOffer_andHas3Entries() throws Exception {
		System.out.println("ID----: "+offerList.get(0).getId());
		mockMvc.perform(delete(this.baseUrl+"/"+offerList.get(0).getId())
				.contentType(contentType))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", Matchers.equalTo( offerList.get(0).getId().intValue() )))
				.andExpect(jsonPath("$.user.id", Matchers.equalTo(offerList.get(0).getUser().getId().intValue())))
				.andExpect(jsonPath("$.has", Matchers.equalTo(offerList.get(0).getHas().toString())))
				.andExpect(jsonPath("$.wants", Matchers.equalTo(offerList.get(0).getWants().toString())))
				.andExpect(jsonPath("$.amount", Matchers.equalTo(offerList.get(0).getAmount().doubleValue())))
				.andExpect(jsonPath("$.rate", Matchers.equalTo(offerList.get(0).getRate().doubleValue())));;
				//.andDo(print());
		
		mockMvc.perform(getOffer(null, null, null, null, null, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(3)));
	}
	

	//GETs
	//offer by id - base/{id}
	//offers by user - base/user/{id}
	//offers by has currency - base/has/{code}
	//offers by wants currency - base/wants/{code}
	//offers by has and wants - base/{code}/{code}
	//offers by has currency and has amount - base/has/{code}/{amount}
	//offers by wants currency and wants amount - base/wants/{code}/{amount}
	//offers by has currency, wants currency and wants amount - base/{code}/{code}/{amount}
	
	private RequestBuilder getOffer(Long offerId, Long userId, CurrencyCode has, BigDecimal hasAmount, CurrencyCode wants, BigDecimal wantsAmount) throws Exception {
		StringBuilder url = new StringBuilder(this.baseUrl);
		
		if (offerId != null) url.append("/"+offerId.toString());
		else if (userId != null) url.append("/user/"+userId);
		else if (has != null && wants != null && wantsAmount != null) url.append("/"+has+"/"+wants+"/"+wantsAmount);
		else if (wants != null && wantsAmount != null) url.append("/wants/"+wants+"/"+wantsAmount);
		else if (has != null && hasAmount != null) url.append("/has/"+has+"/"+hasAmount);
		else if (has != null && wants != null) url.append("/"+has+"/"+wants);
		else if (wants != null) url.append("/wants/"+wants);
		else if (has != null) url.append("/has/"+has);
		
		return get(url.toString())
				.contentType(contentType);		
	}

	/**
	 * Saves a user (if ID is set) using PUT
	 * or Creates a user (ID == null) using POST
	 * */
	private RequestBuilder saveOffer(Offer offer, Long id) throws Exception {
		StringBuilder url = new StringBuilder(this.baseUrl);
		if (id != null){
			url.append("/"+id.toString());
			return put(url.toString())
					.content(this.json(offer))
					.contentType(contentType);
		} else {
			return post(url.toString())
					.content(this.json(offer))
					.contentType(contentType);
		}
		
	}
	

}
