package com.newapp.Webapp.Service.Interface;


import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;

import com.newapp.Webapp.Dto.OrderRequest;
import com.newapp.Webapp.Dto.Response;
import com.newapp.Webapp.enums.OrderStatus;

public interface OrderitemService {
	
	Response placeOrder(OrderRequest orderRequest);
	
	Response updateOrderItemstatus(Long orderItemId, String status);
	
	Response filterorderItem(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate,Long Itemid, Pageable pageable);
}
