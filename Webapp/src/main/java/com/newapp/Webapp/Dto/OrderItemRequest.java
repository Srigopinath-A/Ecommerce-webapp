package com.newapp.Webapp.Dto;

import lombok.Data;

@Data
public class OrderItemRequest {
	private Long productId;
	private int quantity;
}
