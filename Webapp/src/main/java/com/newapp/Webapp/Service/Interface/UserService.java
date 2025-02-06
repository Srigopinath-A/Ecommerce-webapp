package com.newapp.Webapp.Service.Interface;

import com.newapp.Webapp.Dto.Loginrequest;
import com.newapp.Webapp.Dto.Response;
import com.newapp.Webapp.Dto.Userdto;
import com.newapp.Webapp.Entity.User;

public interface UserService {
	
	
	//  this will have a generic response 
	
	
	// first will be a register response 
	Response registeruser(Userdto registrationrequest);
	
	// its is for loginrequest
	Response loginuser(Loginrequest loginrequest);
	
	//This is for get all method 
	Response getAllUser();
	
	// its an another method from Entity 
	User getloginuser();
	
	// its id for getting the user
	Response getuserinfohistory();
	
}
