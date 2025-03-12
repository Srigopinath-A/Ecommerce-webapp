package com.newapp.Webapp.Service.implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newapp.Webapp.Dto.Addressdto;
import com.newapp.Webapp.Dto.Response;
import com.newapp.Webapp.Entity.Address;
import com.newapp.Webapp.Entity.User;
import com.newapp.Webapp.Repo.Addressrepo;
import com.newapp.Webapp.Service.Interface.AddressServices;
import com.newapp.Webapp.Service.Interface.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService implements AddressServices{
	
	@Autowired
	private final Addressrepo addressrepo;
	
	private final UserService userservice;

	@Override
	public Response saveandupdateaddress(Addressdto addressdto){
		User user =  userservice.getloginuser();
		Address address = user.getAddress();
	
		if(address == null) {
			address = new Address();
			address.setUser(user);
		}
		
		if(addressdto.getStreet() != null ) address.setStreet(addressdto.getStreet());
		if(addressdto.getCity() != null ) address.setCity(addressdto.getCity());
		if(addressdto.getState() != null ) address.setState(addressdto.getState());
		if(addressdto.getZipcode() != 0 ) address.setZipcode(address.getZipcode());
		if(addressdto.getCountry() != null ) address.setCountry(addressdto.getCountry());
		
		addressrepo.save(address);
		
		String message = (user.getAddress() == null) ? "Address Successfully Created" : "Address Successfully Updated";
		return Response.builder()
				.status(200)
				.message(message)
				.build();
		
	}
	
	
	
}
