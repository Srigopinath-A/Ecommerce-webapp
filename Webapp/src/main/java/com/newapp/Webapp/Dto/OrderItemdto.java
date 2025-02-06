package com.newapp.Webapp.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.newapp.Webapp.Entity.Order;
import com.newapp.Webapp.Entity.Product;
import com.newapp.Webapp.Entity.User;
import com.newapp.Webapp.enums.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemdto {
	
	private Long id;
	private int quantity;
	private BigDecimal price;
	private String Status;
	
	private Userdto user;
	private Productdto product;
	
	private LocalDateTime createdAt;



}
