package com.tripleftover.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.tripleftover.User;
import com.tripleftover.repository.UserRepository;


@RestController
@RequestMapping("/users")
class UserRestController {
	
	@RequestMapping("")
	Collection<User> users (){
		return this.userRepository.findAll();
	}
	
	@RequestMapping("/{id}")
	User userById (@PathVariable("id") Long id){
		return this.userRepository.findById(id);
	}
	

	
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	User add (@RequestBody User input){
		return this.userRepository.save(input);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	User add (@PathVariable("id") Long id, @RequestBody User input){
		User updatedUser = this.userRepository.findById(id);
		updatedUser.setName(input.getName());
		return this.userRepository.save(updatedUser);
	}
	
	@Autowired UserRepository userRepository; 
}
