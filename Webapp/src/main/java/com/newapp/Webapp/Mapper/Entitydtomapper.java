package com.newapp.Webapp.Mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.newapp.Webapp.Dto.Addressdto;
import com.newapp.Webapp.Dto.OrderItemdto;
import com.newapp.Webapp.Dto.Productdto;
import com.newapp.Webapp.Dto.Userdto;
import com.newapp.Webapp.Entity.Address;
import com.newapp.Webapp.Entity.OrderItem;
import com.newapp.Webapp.Entity.Product;
import com.newapp.Webapp.Entity.User;

@Component
public class Entitydtomapper {
	
	public Userdto mapUserTodtoBasic(User user) {
		Userdto userdto = new Userdto();
		userdto.setId(user.getId());
		userdto.setName(user.getName());
		userdto.setMail(user.getMail());
		userdto.setRole(user.getRole().name());
		return userdto;
	}
	
	public Addressdto mapAddressTodtoBasic(Address address) {
		Addressdto addressdto = new Addressdto();
		addressdto.setId(address.getId());
		addressdto.setCity(address.getCity());
		addressdto.setStreet(address.getStreet());
		addressdto.setState(address.getState());
		addressdto.setCountry(address.getCountry());
		addressdto.setZipcode(address.getZipcode());
		return addressdto;
	}
	
	public OrderItemdto mapOrderItemTodtoBasic(OrderItem orderitem) {
		OrderItemdto orderitemdto = new OrderItemdto();
		orderitemdto.setId(orderitem.getId());
		orderitemdto.setQuantity(orderitem.getQuantity());
		orderitemdto.setPrice(orderitem.getPrice());
		orderitemdto.setStatus(orderitem.getStatus().name());
		orderitemdto.setCreatedAt(orderitem.getCreatedAt());
		return orderitemdto;
	}
	
	public Productdto mapProductTodtoBasic (Product product) {
		Productdto productdto = new Productdto();
		productdto.setId(product.getId());
		productdto.setName(product.getName());
		productdto.setPrice(product.getPrice());
		productdto.setDescription(product.getDescription());
		productdto.setImageUrl(product.getImageUrl());
		return productdto;
	}
	
	public Userdto mapUserTodtoPlusAddress(User user) {
		Userdto userdto = mapUserTodtoBasic(user);
		if(user.getAddress() != null) {
			Addressdto addrressdto = mapAddressTodtoBasic(user.getAddress());
			userdto.setAddress(addrressdto);
		}
		return userdto;
	}
	
	public OrderItemdto mapOrderItemTodtoPlusProduct(OrderItem orderitem) {
		OrderItemdto orderitemdto = mapOrderItemTodtoBasic(orderitem);
		
		if(orderitemdto.getProduct() != null) {
			Productdto productdto = mapProductTodtoBasic(orderitem.getProduct());
			orderitemdto.setProduct(productdto);
		}
		return orderitemdto;
	}
	
	public OrderItemdto mapOrderItemTodtoPlusProductAndUser(OrderItem orderitem) {
		OrderItemdto orderitemdto = mapOrderItemTodtoPlusProduct(orderitem);
		if(orderitem.getUser() != null) {
			Userdto userdto = mapUserTodtoPlusAddress(orderitem.getUser());
			orderitemdto.setUser(userdto); 
		}
		return orderitemdto;
	}
	
	public Userdto mapUserTodtoPlusAddressAndOrderHistory(User user) {
		Userdto userdto = mapUserTodtoPlusAddress(user);
		
		if(user.getOrderItemList() != null && !user.getOrderItemList().isEmpty()) {
			userdto.setOrderItemList(user.getOrderItemList()
					.stream()
					.map(this::mapOrderItemTodtoPlusProduct)
					.collect(Collectors.toList()));
		}
		return userdto;
	}
}
