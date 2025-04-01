package com.newapp.Webapp.Service.implementation;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.newapp.Webapp.Dto.Loginrequest;
import com.newapp.Webapp.Dto.Response;
import com.newapp.Webapp.Dto.Userdto;
import com.newapp.Webapp.Entity.User;
import com.newapp.Webapp.Mapper.Entitydtomapper;
import com.newapp.Webapp.Repo.UserRepo;
import com.newapp.Webapp.Security.JwtUtils;
import com.newapp.Webapp.enums.UserRole;
import com.newapp.Webapp.exception.InvalidCredentialsException;
import com.newapp.Webapp.exception.NotfoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
@RequiredArgsConstructor

public class UserService implements com.newapp.Webapp.Service.Interface.UserService {
	
	private final UserRepo userrepo;
	
	
	private final PasswordEncoder passwordencoder;
	
	private final JwtUtils jwtutils;
	
	private final Entitydtomapper entitydtomapper;
	
	
	
	
	@Override // we are using jsonIgnoreproperties in userdto so it will be avoiding content that we not used in userdto
	public Response registeruser(Userdto registrationrequest) { // if there is no role present in registrationrequest then default will be user.
		UserRole role = UserRole.USER;
		if(registrationrequest.getRole() != null && registrationrequest.getRole().equalsIgnoreCase("admin")) {
			role = UserRole.ADMIN;
		}
		User user =  User.builder()
				.name(registrationrequest.getName())
				.email(registrationrequest.getEmail())
				.password(passwordencoder.encode(registrationrequest.getPassword()))
				.role(role)
				.build();
		
		User saveuser = userrepo.save(user);
		System.out.println(saveuser);	
		
		
		Userdto userdto = entitydtomapper.mapUserTodtoBasic(saveuser);
		return Response.builder()
				.status(200)
				.message("User Successfuly Added")
				.user(userdto)
				.build();
	}

	@Override
	public Response loginuser(Loginrequest loginrequest) {
		User user = userrepo.findByEmail(loginrequest.getEmail()).orElseThrow(()-> new NotfoundException("Email is not found"));
		if(!passwordencoder.matches(loginrequest.getPassword(), user.getPassword())) {
			throw new InvalidCredentialsException("password does not matches");
		}
		
		String token = jwtutils.generateToken(user);
		return Response.builder()
				.status(200)
				.message("user successfully logged in")
				.token(token)
				.expiration("6 Month")
				.role(user.getRole().name())
				.build();
	}

	@Override
	public Response getAllUser() {
		List<User> user = userrepo.findAll();
		List<Userdto> userdto = user.stream()
				.map(entitydtomapper::mapUserTodtoBasic)
				.toList();
		return Response.builder()
				.status(200)
				.message("List of user are shown")
				.build();
	}

	@Override
	public User getloginuser() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	String email = authentication.getName();
	log.info("User email is"+email);
		return userrepo.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Email is not found"));
	}

	@Override
	public Response getuserinfohistory() {
		User user = getloginuser();
		Userdto userdto = entitydtomapper.mapUserTodtoPlusAddressAndOrderHistory(user);
		return Response.builder()
				.status(200)
				.user(userdto)
				.build();
	}

	

	

}
