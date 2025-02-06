package com.newapp.Webapp.Dto;

import lombok.Data;

@Data
public class OrderItemRequest {
	private int productId;
	private int quantity;
}
