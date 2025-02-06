package com.newapp.Webapp.Service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newapp.Webapp.Dto.Addressdto;
import com.newapp.Webapp.Dto.Response;
import com.newapp.Webapp.Repo.Addressrepo;
import com.newapp.Webapp.Service.Interface.AddressServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService implements AddressServices{
	
	@Autowired
	private final Addressrepo addressrepo;
	
	private final UserService userservice;

	@Override
	public Response saveandupdateaddress(Addressdto addressdto) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
