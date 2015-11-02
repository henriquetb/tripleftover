/**
 * 
 */
package com.tripleftover;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tripleftover.Util.CurrencyCode;

public class CurrencyEntityTest {
	
	Currency aud; 
	@Before
	public void setUp() throws Exception {
		this.aud = getCurrency(CurrencyCode.AUD, "Australian Dollar", "AUS");
	}
	
	private Currency getCurrency(CurrencyCode code, String name, String region){
		return new Currency(code, name, region);
	}

	/**
	 * Test method for {@link com.tripleftover.Currency#Currency(CurrencyCode, String, String)}.
	 */
	@Test
	public void testCurrencyContructor_AUD_AustralianDollar_AU() {
		Currency aud = getCurrency(CurrencyCode.AUD, "Australian Dollar", "AUS");
	}

	/**
	 * Test method for {@link com.tripleftover.Currency#getId()}.
	 */
	@Test
	public void testGetId() {
		assertEquals(CurrencyCode.AUD, this.aud.getId());
	}

	/**
	 * Test method for {@link com.tripleftover.Currency#setId(CurrencyCode)}.
	 */
	@Test
	public void testSetId() {
		CurrencyCode newCode = CurrencyCode.BRL;
		this.aud.setId(newCode);
		assertEquals(newCode, this.aud.getId());
		
	}

	/**
	 * Test method for {@link com.tripleftover.Currency#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals("Australian Dollar", this.aud.getName());
	}

	/**
	 * Test method for {@link com.tripleftover.Currency#setName(String)}.
	 */
	@Test
	public void testSetName() {
		String newName = "Brazilian Real";
		this.aud.setName(newName);
		assertEquals(newName, this.aud.getName());
	}

	/**
	 * Test method for {@link com.tripleftover.Currency#getRegion()}.
	 */
	@Test
	public void testGetRegion() {
		assertEquals("AUS", this.aud.getRegion());
	}

	/**
	 * Test method for {@link com.tripleftover.Currency#setRegion(String)}.
	 */
	@Test
	public void testSetRegion() {
		String newRegion = "BRA";
		this.aud.setRegion(newRegion);
		assertEquals(newRegion, this.aud.getRegion());
	}

}
