package com.tripleftover.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tripleftover.User;


public interface UserRepository extends JpaRepository<User, Long>{
	Collection<User> findByNameLikeIgnoreCase(String name);
	User findById(Long id);
}
