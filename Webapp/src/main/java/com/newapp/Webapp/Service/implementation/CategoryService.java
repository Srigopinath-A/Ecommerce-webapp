package com.newapp.Webapp.Service.implementation;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.newapp.Webapp.exception.*;
import com.newapp.Webapp.Dto.Categorydto;
import com.newapp.Webapp.Dto.Response;
import com.newapp.Webapp.Entity.Category;
import com.newapp.Webapp.Mapper.Entitydtomapper;
import com.newapp.Webapp.Repo.CategoryRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService implements com.newapp.Webapp.Service.Interface.CategoryService {
	
	private final CategoryRepo categoryrepo;
	private final Entitydtomapper entitydtomapper;

	@Override
	public Response createcategory(Categorydto categoryRequest) {
		Category category = new Category();
		category.setName(categoryRequest.getName());
		categoryrepo.save(category);
		return Response.builder()
				.status(200)
				.message("category created successfully")
				.build();
	}

	@Override
	public Response updatecategory(Long categoryid, Categorydto categoryRequest) {
		Category category = categoryrepo.findById(categoryid).orElseThrow(()-> new NotfoundException("Category Not Found"));
		category.setName(categoryRequest.getName());
		categoryrepo.save(category);
		return Response.builder()
				.status(200)
				.message("category update successfully")
				.build();
	}

	@Override
	public Response getAllcategories() {
		List<Category> category = categoryrepo.findAll();
		List<Categorydto> categorylist = category.stream()
				.map(entitydtomapper::mapCategoryTodtoBasic)
				.collect(Collectors.toList());
		return Response.builder()
				.status(200)
				.categoryList(categorylist)
				.build();
	}

	@Override
	public Response getcategorybyid(Long categoryid) {
		Category category =  categoryrepo.findById(categoryid).orElseThrow(() -> new NotfoundException("category not found"));
		Categorydto categorydto = entitydtomapper.mapCategoryTodtoBasic(category);
		return Response.builder()
				.status(200)
				.category(categorydto)
				.build();
	}

	@Override
	public Response deletecategory(Long categoryid) {
		Category category = categoryrepo.findById(categoryid).orElseThrow(() -> new NotfoundException(" Category not found"));
		categoryrepo.delete(category);
		return Response.builder().status(200).message("deleted message").build();
	}

}
