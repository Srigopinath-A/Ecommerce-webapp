package com.newapp.Webapp.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newapp.Webapp.Entity.User;

public interface UserRepo extends JpaRepository<User, Long>{
	
	
	Optional <User> findByEmail(String email);
}
