package com.newapp.Webapp.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private BigDecimal totalPrice;
	
	//cascade if you delete the order it will delete the order items 
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY , cascade = CascadeType.ALL, orphanRemoval = true )
	private List<OrderItem> orderItemList;
	
	@Column(name = "created_at")
	private final LocalDateTime createdAt = LocalDateTime.now();
}

	
