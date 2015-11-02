package com.tripleftover;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.tripleftover.repository.CurrencyRepository;
import com.tripleftover.repository.OfferRepository;
import com.tripleftover.repository.UserRepository;


/**
 * This test class will test the contoller by mocking the api endpoint responses.
 * No need to have a functional server running. 
 * */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TripLeftoverApplication.class)
@WebAppConfiguration 
public class RestControllersTest {
	
	protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), 
			MediaType.APPLICATION_JSON.getSubtype(), 
			Charset.forName("utf8"));
	
	protected MockMvc mockMvc;
	
	protected HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	@Autowired
	protected WebApplicationContext webApplicationContext;
	
	@Autowired
	protected void setConverters(HttpMessageConverter<?>[] converters) {
		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
				hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
		
		assertNotNull("JSON converter cannot be null", this.mappingJackson2HttpMessageConverter);
	}

	@Autowired UserRepository userRepository;
	@Autowired CurrencyRepository currencyRepository;
	@Autowired OfferRepository offerRepository;
	
	@Before
	public void setUp(){
		mockMvc = webAppContextSetup(webApplicationContext).build();
		
		//cleans all previous loaded DB values
		this.offerRepository.deleteAllInBatch();
		this.userRepository.deleteAllInBatch();
		this.currencyRepository.deleteAllInBatch();
	}
	
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}
