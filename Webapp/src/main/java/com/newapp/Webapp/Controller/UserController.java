package com.newapp.Webapp.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newapp.Webapp.Dto.Response;
import com.newapp.Webapp.Service.implementation.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userservice;
	
	@GetMapping("/get-all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> getAlluser(){
		return ResponseEntity.ok(userservice.getAllUser());
	}
	
	@GetMapping("/my-info")
	public ResponseEntity<Response> getUserinfoandOrdehistory(){
		return ResponseEntity.ok(userservice.getuserinfohistory());
	}
}
