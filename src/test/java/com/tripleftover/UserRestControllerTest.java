package com.tripleftover;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.RequestBuilder;

import com.tripleftover.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


/**
 * This test class will test the contoller by mocking the api endpoint responses.
 * No need to have a functional server running. 
 * */
public class UserRestControllerTest extends RestControllersTest{
	
	private String baseUrl = "/users";
	
	private List<User> userList = new ArrayList<User>();
	
	@Before
	public void setUp(){
		super.setUp();
		
		userList.add(userRepository.save(new User("Henrique")));
		userList.add(userRepository.save(new User("John")));
	}
	
	

	@Test
	public void findAll_returns2Users() throws Exception {
		mockMvc.perform(getUser(null, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(2)));
	}
	
	@Test
	public void findById_NonExistingId_returnsEmptyString() throws Exception {
		mockMvc.perform(getUser(Long.MAX_VALUE, null))
				.andExpect(status().isOk())
				.andExpect(content().string(""));
				//.andDo(print());
	}
	
	@Test
	public void findById_ExistingId_returnsUserAttributes() throws Exception {
		mockMvc.perform(getUser(userList.get(0).getId(), null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", Matchers.equalTo(userList.get(0).getId().intValue())))
				.andExpect(jsonPath("$.name", Matchers.equalTo(userList.get(0).getName())));
	}
	

	
	@Test
	public void findByName_NonExistingName_returnsEmptyArray() throws Exception {
		mockMvc.perform(getUser(null, "noname"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(0)));
				//.andDo(print());
	}
	
	@Test
	public void findByName_ExistingPartial_returns2UserAttributes() throws Exception {
		mockMvc.perform(getUser(null, "h"))
				.andExpect(status().isOk())
				
				.andExpect(jsonPath("$", Matchers.hasSize(2)))
				
				.andExpect(jsonPath("$[0].id", Matchers.equalTo(userList.get(0).getId().intValue())))
				.andExpect(jsonPath("$[0].name", Matchers.equalTo(userList.get(0).getName())))
				.andExpect(jsonPath("$[1].id", Matchers.equalTo(userList.get(1).getId().intValue())))
				.andExpect(jsonPath("$[1].name", Matchers.equalTo(userList.get(1).getName())));
	}
	

	
	@Test
	public void add_returnsNewUserAttributes_AndHas3Entries() throws Exception {
		User newUser = new User("Bilbo");
		mockMvc.perform(saveUser(newUser, null))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value( Matchers.isA(Integer.class)))
				.andExpect(jsonPath("$.name", Matchers.equalTo(newUser.getName())));
		
		mockMvc.perform(getUser(null, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(3)));
	}


	
	@Test
	public void update_returnsUserWithNewAttributes_AndHas2Entries() throws Exception {
		String newName = "newName";
		userList.get(0).setName(newName);
		mockMvc.perform(saveUser(userList.get(0), userList.get(0).getId()))
				.andExpect(jsonPath("$.id", Matchers.equalTo( userList.get(0).getId().intValue() )))
				.andExpect(jsonPath("$.name", Matchers.equalTo(newName)));
		

		mockMvc.perform(getUser(null, null))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(2)));
	}
	
	
	
	
	private RequestBuilder getUser(Long id, String name) throws Exception {
		StringBuilder url = new StringBuilder(this.baseUrl);
		if (id != null) url.append("/"+id.toString());
		else if (name != null) url.append("/name/"+name);
		
		return get(url.toString())
				.contentType(contentType);		
	}

	/**
	 * Saves a user (if ID is set) using PUT
	 * or Creates a user (ID == null) using POST
	 * */
	private RequestBuilder saveUser(User user, Long id) throws Exception {
		StringBuilder url = new StringBuilder(this.baseUrl);
		if (id != null){
			url.append("/"+id.toString());
			return put(url.toString())
					.content(this.json(user))
					.contentType(contentType);
		} else {
			return post(url.toString())
					.content(this.json(user))
					.contentType(contentType);
		}
		
	}
	

}
