package com.newapp.Webapp.Service.implementation;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.newapp.Webapp.Dto.Productdto;
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
		Category category = categoryrepo.findById(categoryid)
				.orElseThrow(() -> new NotfoundException("Category not found"));
		String producturl = azureservice.saveimageblob(image);

		Product product = new Product();
		product.setCategory(category);
		product.setPrice(price);
		product.setName(name);
		product.setDescription(description);
		product.setImageUrl(producturl);

		productrepo.save(product);
		return Response.builder().status(200).message("product successfully created").build();
	}

	@Override
	public Response updateproduct(Long productid, Long categoryid, MultipartFile image, String name, String description,
			BigDecimal price) {
		Product product = productrepo.findById(productid).orElseThrow(() -> new NotfoundException("Product not foud"));

		Category category = null;
		
		String producturl = null;

		if (category != null) {
			category = categoryrepo.findById(categoryid).orElseThrow(() -> new NotfoundException("Category Not Found"));
		}
		
		if( image != null && !image.isEmpty()) {
			producturl = azureservice.saveimageblob(image);
		}
		
		if(category != null) product.setCategory(category);
		if(name != null) product.setName(name);
		if(price != null) product.setPrice(price);
		if(description != null) product.setDescription(description);
		if(producturl != null) product.setImageUrl(producturl);
		
		productrepo.save(product);

		return Response.builder().status(200).message("Product updated Successfully ").build();
	}

	@Override
	public Response deleteproduct(Long productid) {
		Product product = productrepo.findById(productid).orElseThrow(() -> new NotfoundException(" Product Not Found"));
		productrepo.delete(product);
		
		return Response.builder().status(200).message("Product deleted successfully").build();
	}

	@Override
	public Response getProductByid(Long productid) {
		Product product = productrepo.findById(productid).orElseThrow(() -> new NotfoundException("Product Not Found"));
		Productdto productdto = entitydtomapper.mapProductTodtoBasic(product);
		
		return Response.builder().product(productdto).build();
	}

	@Override
	public Response getAllproducts() {
		List<Productdto> productlist  = productrepo.findAll(Sort.by(Sort.Direction.DESC, "id"))
				.stream().map(entitydtomapper::mapProductTodtoBasic)
				.collect(Collectors.toList());
		return Response.builder().status(200).productList(productlist).build();
	}

	@Override
	public Response getProductByCategory(Long categoryid) {
		List<Product> products = productrepo.findByCategoryId(categoryid);
		if(products.isEmpty()) {
			throw new NotfoundException("No product Found for this category");
		}
		
		List<Productdto> productdto = products.stream().map(entitydtomapper::mapProductTodtoBasic).collect(Collectors.toList());
		
		
		return Response.builder().status(200).productList(productdto).build();
}

	@Override
	public Response searchproduct(String Searchvalue) {
		List<Product> product = productrepo.findByNameOrDescriptionContaining(Searchvalue, Searchvalue);
		
		if(product.isEmpty()) {
			throw new NotfoundException("No products are avaliable");
		}
		List<Productdto> productdto = product.stream().map(entitydtomapper::mapProductTodtoBasic).collect(Collectors.toList());
		return Response.builder().status(200).productList(productdto).build();
	}

}
