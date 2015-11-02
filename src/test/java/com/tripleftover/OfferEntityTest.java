package com.tripleftover;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.tripleftover.Util.CurrencyCode;

import static org.mockito.Mockito.*;

public class OfferEntityTest {
	Offer offer; 
	User mockedUser;
	
	@Before
	public void setUp() throws Exception {
		mockedUser = mock(User.class);
		//when(mockedUser.getId()).thenReturn(new Long(1));
		//when(mockedUser.getName()).thenReturn("Henrique");
		
		this.offer = getOffer(mockedUser, CurrencyCode.AUD, CurrencyCode.EUR, new BigDecimal(100), 0.7);
	}
	
	private Offer getOffer(User user, CurrencyCode has, CurrencyCode wants, BigDecimal amount, Double rate){
		return new Offer(user, has, wants, amount, rate);
	}

	@Test
	public void testOfferConstructor_MockUser_AUD_EUR_100_07D() {
		Offer offer = new Offer(mockedUser, CurrencyCode.AUD, CurrencyCode.EUR, new BigDecimal(100), 0.7);
	}

	@Test
	public void testGetId() {
		assertNull(this.offer.getId());
	}

	@Test
	public void testSetId() {
		Long newId = new Long(1);
		this.offer.setId(newId);
		assertEquals(newId, this.offer.getId());
	}

	@Test
	public void testGetUser() {
		assertEquals(mockedUser, this.offer.getUser());
	}

	@Test
	public void testSetUser() {
		User newMockedUser = mock(User.class);
		this.offer.setUser(newMockedUser);
		assertEquals(newMockedUser, this.offer.getUser());
	}

	@Test
	public void testGetHas() {
		assertEquals(CurrencyCode.AUD, this.offer.getHas());
	}

	@Test
	public void testSetHas() {
		CurrencyCode newHas = CurrencyCode.BRL;
		this.offer.setHas(newHas);
		assertEquals(newHas, this.offer.getHas());
	}

	@Test
	public void testGetWants() {
		assertEquals(CurrencyCode.EUR, this.offer.getWants());
	}

	@Test
	public void testSetWants() {
		CurrencyCode newWants = CurrencyCode.USD;
		this.offer.setWants(newWants);
		assertEquals(newWants, this.offer.getWants());
	}

	@Test
	public void testGetAmount() {
		assertEquals(new BigDecimal(100), this.offer.getAmount());
	}

	@Test
	public void testSetAmount() {
		BigDecimal newAmount = new BigDecimal(400);
		this.offer.setAmount(newAmount);
		assertEquals(newAmount, this.offer.getAmount());
	}

	@Test
	public void testGetRate() {
		assertEquals(new Double(0.7), this.offer.getRate());
	}

	@Test
	public void testSetRate() {
		Double newRate = new Double(2.12);
		this.offer.setRate(newRate);
		assertEquals(newRate, this.offer.getRate());
	}

	@Test
	public void testGetDeal() {
		assertNull(this.offer.getDeal());
	}

	@Test
	public void testSetDeal() {
		User newMockedUser = mock(User.class);
		this.offer.setDeal(newMockedUser);
		assertEquals(newMockedUser, this.offer.getDeal());
	}

}
