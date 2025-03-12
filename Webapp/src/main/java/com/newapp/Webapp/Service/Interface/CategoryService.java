package com.newapp.Webapp.Service.Interface;

import com.newapp.Webapp.Dto.Categorydto;
import com.newapp.Webapp.Dto.Response;

public interface CategoryService {
	
	Response createcategory( Categorydto categoryRequest);
	
	Response updatecategory(Long categoryid, Categorydto categoryRequest);
	
	Response getAllcategories();
	
	Response getcategorybyid(Long categoryid);
	
	Response deletecategory(Long categoryid);
	
}
