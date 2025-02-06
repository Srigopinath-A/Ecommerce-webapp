package com.newapp.Webapp.Dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.newapp.Webapp.Entity.Payment;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequest {
	private BigDecimal totalprice;
	private List<OrderItemRequest> items;
	private Payment paymentInfo;
}
