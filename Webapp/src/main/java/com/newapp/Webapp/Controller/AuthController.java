package com.newapp.Webapp.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newapp.Webapp.Dto.Loginrequest;
import com.newapp.Webapp.Dto.Response;
import com.newapp.Webapp.Dto.Userdto;
import com.newapp.Webapp.Service.implementation.UserService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor //this could solve the userservice  not initalized issue.
public class AuthController {
	
	private final UserService userservice;
	
	@PostMapping("/register")
	public ResponseEntity<Response> Registeruser(@RequestBody Userdto registratiorequest){
		return ResponseEntity.ok(userservice.registeruser(registratiorequest));
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> Loginuser(@RequestBody Loginrequest loginrequest){
		return ResponseEntity.ok(userservice.loginuser(loginrequest));
	}
	
}
