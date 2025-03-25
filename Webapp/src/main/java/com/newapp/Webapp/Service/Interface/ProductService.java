package com.newapp.Webapp.Service.Interface;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import com.newapp.Webapp.Dto.Response;

public interface ProductService {

	Response createproduct(Long categoryid,MultipartFile image, String name, String description,BigDecimal price );
	
	Response updateproduct ( Long productid, Long categoryid,MultipartFile image, String name,String description, BigDecimal price);
	
	Response deleteproduct (Long productid);
	
	Response getProductByid(Long productid);
	
	Response getAllproducts();
	
	Response getProductByCategory(Long categoryid);
	
	Response searchproduct(String Searchvalue);
	
}
