package com.newapp.Webapp.Specification;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.newapp.Webapp.Entity.OrderItem;
import com.newapp.Webapp.enums.OrderStatus;

import jakarta.persistence.criteria.CriteriaBuilder;

public class OrderItemSpecification {
	
	//in this we going to add items based on 3 specifications 
	//1. order item by status 
	//2.find order date by start date and end date 
	//3.get order item by id.
	
	// this search for id and Start date and end date also search for status
	//also specification to filter order item by status 
	private static Specification<OrderItem> hasStatus(OrderStatus status){
		return ((root, query, CriteriaBuilder) -> status != null ? CriteriaBuilder.equal(root.get("status"), status) : null);
	}
	
	//to filter using start date and end date 
	// also specification to filter order item by data range 
	public static Specification<OrderItem> createdBetween(LocalDateTime startdate,LocalDateTime enddate){
		return ((root, query, criteriaBuilder) -> {
			if(startdate != null && enddate != null) {
				return criteriaBuilder.between(root.get("createdAt"), startdate, enddate);
			} else if(startdate != null) {
				return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startdate);
			}else if(enddate != null) {
				return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), enddate);
			}else {
				return null;
			}
		});
	}
	
	
	// Generate specification to filter orderitem by item id
	public static Specification<OrderItem> hasItemId(Long itemId){
		return ((root, query, criteriaBuilder) -> 
			itemId != null ? criteriaBuilder.equal(root.get("id"),itemId) : null);
		}
	}
	
