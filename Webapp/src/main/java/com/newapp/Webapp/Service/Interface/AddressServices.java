package com.newapp.Webapp.Service.Interface;

import com.newapp.Webapp.Dto.Addressdto;
import com.newapp.Webapp.Dto.Response;

// this will have the function to save and update the address.

public interface AddressServices {
	Response saveandupdateaddress(Addressdto addressdto);
}
