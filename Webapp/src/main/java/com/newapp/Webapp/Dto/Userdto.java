package com.newapp.Webapp.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.newapp.Webapp.Entity.Address;
import com.newapp.Webapp.Entity.OrderItem;
import com.newapp.Webapp.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Userdto {
	
	private long id;
	private String name;
	private String email;
	
	private String password;

	private Addressdto Address;
	private String role;
	private List<OrderItemdto> orderItemList;
	
	private LocalDateTime createdAt;



}
