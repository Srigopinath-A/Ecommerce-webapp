package com.newapp.Webapp.Dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.newapp.Webapp.Entity.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
	
	private int status;
	private String message;
	private final LocalDateTime createdat = LocalDateTime.now();
	
	private String token;
	private String role;
	private String expiration;
	
	private int totalPage;
	private long totalElement;
	
	private Addressdto address;
	private Userdto user;
	private List<Userdto> userlist;
	
	private Categorydto category;
	private List<Categorydto> categoryList;
	
	private Productdto product;
	private List<Productdto> productList;
	
	private OrderItemdto orderItem;
	private List<OrderItemdto> orderItemList;
	
	private Orderdto order;
	private List<Orderdto> orderList;
	
}
