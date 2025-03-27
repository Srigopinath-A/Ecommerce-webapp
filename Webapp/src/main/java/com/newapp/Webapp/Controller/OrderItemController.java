package com.newapp.Webapp.Controller;


import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newapp.Webapp.Dto.OrderRequest;
import com.newapp.Webapp.Dto.Response;
import com.newapp.Webapp.Service.Interface.OrderitemService;
import com.newapp.Webapp.enums.OrderStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderItemController {
	
	private final OrderitemService orderitemservice;
	
	@PostMapping("/order")
	public ResponseEntity<Response> placeorder(@RequestBody OrderRequest orderRequest){
		return ResponseEntity.ok(orderitemservice.placeOrder(orderRequest));
	}
	
	@PutMapping("/update-item-status/{orderitemid}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<Response> updateorderitemstatus(@PathVariable Long orderitem, @RequestParam String status){
		return ResponseEntity.ok(orderitemservice.updateOrderItemstatus(orderitem, status));
	}
	
	public ResponseEntity<Response> filterorderitems(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE.DATE_TIME)LocalDateTime startdate,@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE.DATE_TIME)LocalDateTime enddate,
			@RequestParam(required = false) String status, @RequestParam(required = false) Long itemid,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1000" ) int size){
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"id"));
		
		OrderStatus orderstatus = status != null ? OrderStatus.valueOf(status.toUpperCase()) : null;
		
		return ResponseEntity.ok(orderitemservice.filterorderItem(orderstatus, startdate, enddate, itemid, pageable));
	}
}
