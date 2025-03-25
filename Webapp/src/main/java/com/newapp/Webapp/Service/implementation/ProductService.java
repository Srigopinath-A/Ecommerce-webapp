package com.newapp.Webapp.Service.implementation;

import java.math.BigDecimal;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.newapp.Webapp.Dto.Response;
import com.newapp.Webapp.Entity.Category;
import com.newapp.Webapp.Entity.Product;
import com.newapp.Webapp.Mapper.Entitydtomapper;
import com.newapp.Webapp.Repo.CategoryRepo;
import com.newapp.Webapp.Repo.ProductRepo;
import com.newapp.Webapp.Service.Interface.AzureService;
import com.newapp.Webapp.exception.NotfoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService implements com.newapp.Webapp.Service.Interface.ProductService {
	
	private final ProductRepo productrepo;
	private final AzureService azureservice;
	private final Entitydtomapper entitydtomapper;
	private final CategoryRepo categoryrepo;
	
	
	

	@Override
	public Response createproduct(Long categoryid, MultipartFile image, String name, String description,
			BigDecimal price) {
		Category category = categoryrepo.findById(categoryid).orElseThrow(() -> new NotfoundException("Category not found"));
		String producturl = azureservice.saveimageblob(image);
		
		Product product = new Product();
		product.setCategory(category);
		product.setPrice(price);
		product.setName(name);
		product.setDescription(description);
		product.setImageUrl(producturl);
		
		productrepo.save(product);
		return Response.builder()
				.status(200)
				.message("product successfully created")
				.build();
	}

	@Override
	public Response updateproduct(Long productid, Long categoryid, MultipartFile image, String name, String description,
			BigDecimal price) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deleteproduct(Long productid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getProductByid(Long productid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getAllproducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getProductByCategory(Long categoryid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response searchproduct(String Searchvalue) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
