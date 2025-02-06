package com.newapp.Webapp.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newapp.Webapp.Entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
	
	//to find all the product by cateegory id .
	List<Product> findByCategoryId(Long categoryId);
	
	
	//to find all the description of all the items 
	List<Product> findByNameOrDescriptionContaining(String name,String description);
}
