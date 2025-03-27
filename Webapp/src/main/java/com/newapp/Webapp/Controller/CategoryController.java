package com.newapp.Webapp.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newapp.Webapp.Dto.Categorydto;
import com.newapp.Webapp.Dto.Response;
import com.newapp.Webapp.Service.implementation.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryservice;

	@PostMapping("/create")
	public ResponseEntity<Response> createcategory(@RequestBody Categorydto categorydto) {
		return ResponseEntity.ok(categoryservice.createcategory(categorydto));
	}

	@GetMapping("/get-all")
	public ResponseEntity<Response> getAllcategories() {
		return ResponseEntity.ok(categoryservice.getAllcategories());
	}

	@DeleteMapping("/update/{categoryid}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<Response> updatecategory(@PathVariable Long categoryid,
			@RequestBody Categorydto categorydto) {
		return ResponseEntity.ok(categoryservice.updatecategory(categoryid, categorydto));
	}
	
	@DeleteMapping("/delete/{categoryid}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<Response> deletecategory(@PathVariable Long categoryid){
		return ResponseEntity.ok(categoryservice.deletecategory(categoryid));
	}
	
	@GetMapping("/get-category-by-id/{categoryid}")
	public ResponseEntity<Response> getcategoryByid(@PathVariable Long categoryid ){
		return ResponseEntity.ok(categoryservice.getcategorybyid(categoryid));
	}
	
}
