/**
 * 
 */
package com.tripleftover;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author htb
 *
 */
public class UserEntityTest {

	User henrique; 
	
	@Before
	public void setUp() throws Exception {
		this.henrique = getUser("Henrique");
	}
	
	private User getUser(String name){
		return new User(name);
	}

	/**
	 * Test method for {@link com.tripleftover.User#User(java.lang.String)}.
	 */
	@Test
	public void testUserContructor_Henrique() {
		User henrique = new User("Henrique");
	}

	/**
	 * Test method for {@link com.tripleftover.User#getId()}.
	 */
	@Test
	public void testGetId() {
		assertNull(this.henrique.getId());
	}

	/**
	 * Test method for {@link com.tripleftover.User#setId(java.lang.Long)}.
	 */
	@Test
	public void testSetId() {
		Long newId = new Long(1);
		this.henrique.setId(newId);
		assertEquals(newId, this.henrique.getId());
	}

	/**
	 * Test method for {@link com.tripleftover.User#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals("Henrique", this.henrique.getName());
	}

	/**
	 * Test method for {@link com.tripleftover.User#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		String newName = "Username";
		this.henrique.setName(newName);
		assertEquals(newName, this.henrique.getName());
	}

}
