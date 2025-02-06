package com.newapp.Webapp.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.newapp.Webapp.enums.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "order_items ")
public class OrderItem {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int quantity;
	private BigDecimal price;
	private OrderStatus Status;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order-id")
	private Order order;
	
	@Column(name = "created-at")
	private final LocalDateTime createdAt = LocalDateTime.now();
}
