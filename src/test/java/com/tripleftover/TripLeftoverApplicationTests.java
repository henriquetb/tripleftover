package com.tripleftover;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//import com.jayway.restassured.RestAssured;
import com.tripleftover.Util.CurrencyCode;
import com.tripleftover.repository.CurrencyRepository;
import com.tripleftover.repository.OfferRepository;
import com.tripleftover.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TripLeftoverApplication.class)
@WebAppConfiguration
public class TripLeftoverApplicationTests {
	@Autowired  OfferRepository offerRepository;
	@Autowired  UserRepository userRepository;
	@Autowired  CurrencyRepository currencyRepository;

	List<Offer> offers;
	List<User> users;
	List<Currency> currencies;

	@Before
	public void setUp() throws Exception {
		/*currencies = new ArrayList<Currency>();
		currencies.add(new Currency(CurrencyCode.USD, "American Dollar", "USA"));
		currencies.add(new Currency(CurrencyCode.AUD, "Australian Dollar", "AUS"));
		currencies.add(new Currency(CurrencyCode.EUR, "Euro", "EU"));
		currencies.add(new Currency(CurrencyCode.BRL, "BRazilian Real", "BRA"));
		currencyRepository.save(currencies);
		
		users = new ArrayList<User>();
		users.add(new User("Henrique"));
		users.add(new User("Bilbo"));
		users.add(new User("Frodo"));
		userRepository.save(users);
		
		offers = new ArrayList<Offer>();
		offers.add(new Offer(users.get(0), CurrencyCode.AUD, CurrencyCode.BRL, new BigDecimal(100), 2.8));
		offers.add(new Offer(users.get(0), CurrencyCode.AUD, CurrencyCode.EUR, new BigDecimal(200), 0.7));
		offers.add(new Offer(users.get(1), CurrencyCode.AUD, CurrencyCode.EUR, new BigDecimal(400), 0.71));
		offers.add(new Offer(users.get(1), CurrencyCode.USD, CurrencyCode.EUR, new BigDecimal(500), 0.9));
		
		offerRepository.save(offers);*/
		
	}
	@Test
	public void contextLoads() {
		/*RestAssured.when().
			get("/offers/has/aud").
			then().
		        statusCode(200).
		        body("size()", Matchers.is(3)).
		        body("[0].has", Matchers.is("AUD")).
		        body("[0].wants", Matchers.is("EUR"));*/
		 
		assertTrue(true);
	}

}
