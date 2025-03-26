package com.newapp.Webapp.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newapp.Webapp.Dto.Addressdto;
import com.newapp.Webapp.Dto.Response;
import com.newapp.Webapp.Service.implementation.AddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
	
	private final AddressService addressservice;
	
	public ResponseEntity<Response> saveandupdatAddress(@RequestBody Addressdto addressdto){
		
		return ResponseEntity.ok(addressservice.saveandupdateaddress(addressdto));
	}
}
