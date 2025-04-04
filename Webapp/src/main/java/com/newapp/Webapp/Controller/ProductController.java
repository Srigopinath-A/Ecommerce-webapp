package com.newapp.Webapp.Controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.newapp.Webapp.Dto.Response;
import com.newapp.Webapp.Service.Interface.ProductService;
import com.newapp.Webapp.exception.InvalidCredentialsException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productservice;

	@PostMapping("/create")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> createproduct(@RequestParam Long categoryid, @RequestParam MultipartFile image,
			@RequestParam String name, @RequestParam String description, @RequestParam BigDecimal price) {

		if (categoryid == null || image.isEmpty() || description.isEmpty() || price == null) {
			throw new InvalidCredentialsException(" All fields are Required ");
		}
		return ResponseEntity.ok(productservice.createproduct(categoryid, image, name, description, price));
	}
	
	
	@PutMapping("/update/{productid}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> updateproduct(@RequestParam Long productid,@RequestParam(required = false) Long categoryid,@RequestParam(required = false) MultipartFile image,
			@RequestParam(required = false) String name, @RequestParam(required = false) String description,@RequestParam(required = false) BigDecimal price){
		
		return ResponseEntity.ok(productservice.updateproduct(productid, categoryid, image, name, description, price));
	}
	
	@DeleteMapping("/delete/{productid}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> deleteproduct(@PathVariable Long productid){
		return ResponseEntity.ok(productservice.deleteproduct(productid));
	}
	
	@GetMapping("/get-all-product/{productid}")
	public ResponseEntity<Response> getbyproductid(@PathVariable Long productid){
		return ResponseEntity.ok(productservice.getProductByid(productid));
	}
	
	@GetMapping("/get-all")
	public ResponseEntity<Response> getallproduct(){
		return ResponseEntity.ok(productservice.getAllproducts());
	}
	
	@GetMapping("/get-by-categoru-id/{categoryid}")
	public ResponseEntity<Response> getproductbycategory(@PathVariable Long categoryid){
		return ResponseEntity.ok(productservice.getProductByCategory(categoryid));
	}
	
	@GetMapping("/search")
	public ResponseEntity<Response> searchforentity(@RequestParam String servicevalue){
		return ResponseEntity.ok(productservice.searchproduct(servicevalue));
	}
}
