package com.newapp.Webapp.Service.implementation;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.azure.core.http.rest.Page;
import com.newapp.Webapp.Dto.OrderItemdto;
import com.newapp.Webapp.Dto.OrderRequest;
import com.newapp.Webapp.Dto.Response;
import com.newapp.Webapp.Entity.Order;
import com.newapp.Webapp.Entity.OrderItem;
import com.newapp.Webapp.Entity.Product;
import com.newapp.Webapp.Entity.User;
import com.newapp.Webapp.Mapper.Entitydtomapper;
import com.newapp.Webapp.Repo.OrderItemRepo;
import com.newapp.Webapp.Repo.OrderRepo;
import com.newapp.Webapp.Repo.ProductRepo;
import com.newapp.Webapp.Service.Interface.OrderitemService;
import com.newapp.Webapp.Specification.OrderItemSpecification;
import com.newapp.Webapp.enums.OrderStatus;
import com.newapp.Webapp.exception.NotfoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemService implements OrderitemService {

	private final OrderRepo orderrepo;
	private final OrderItemRepo orderitemrepo;
	private final ProductRepo productrepo;
	private final UserService userservice;
	private final Entitydtomapper entitydtomapper;

	@Override
	public Response placeOrder(OrderRequest orderRequest) {
		User user = userservice.getloginuser();

		List<OrderItem> orderitems = orderRequest.getItems().stream().map(OrderItemRequest -> {
			Product product = productrepo.findById(OrderItemRequest.getProductId())
					.orElseThrow(() -> new NotfoundException("Product not found"));

			OrderItem orderitem = new OrderItem();
			orderitem.setProduct(product);
			orderitem.setQuantity(OrderItemRequest.getQuantity());
			orderitem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(OrderItemRequest.getQuantity())));
			orderitem.setStatus(OrderStatus.PENDING);
			orderitem.setUser(user);
			return orderitem;
		}).collect(Collectors.toList());
		
		
		//to calculate total price
		BigDecimal totalprice = orderRequest.getTotalprice() != null && orderRequest.getTotalprice().compareTo(BigDecimal.ZERO) > 0
		? orderRequest.getTotalprice()	
		: orderitems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
 		
		//create an order Entity
		Order order = new Order();
		order.setOrderItemList(orderitems);
		order.setTotalPrice(totalprice);
		
		
		//set the order reference in each orderitem
		orderitems.forEach(orderitem -> orderitem.setOrder(order));
		orderrepo.save(order);
	
		 return Response.builder().status(200)
				 .message("Order was successfully placed").build();
	}

	@Override
	public Response updateOrderItemstatus(Long orderItemId, String status) {
		OrderItem orderitem = orderitemrepo.findById(orderItemId)
				.orElseThrow(()-> new NotfoundException("Order Item not found"));
		
		orderitem.setStatus(OrderStatus.valueOf(status.toUpperCase()));
		orderitemrepo.save(orderitem);
		return Response.builder().status(200).message("Order status updated successfully").build();
	}

	@Override
	public Response filterorderItem(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long Itemid,
			Pageable pageable) {
		
		Specification<OrderItem> spec = Specification.where(OrderItemSpecification.hasStatus(status))
				.and(OrderItemSpecification.createdBetween(startDate, endDate))
				.and(OrderItemSpecification.hasItemId(Itemid));
		
		
		org.springframework.data.domain.Page<OrderItem> orderItemPage = orderitemrepo.findAll(spec, pageable);
		
		if(orderItemPage.isEmpty()) {
			throw new NotfoundException("No order Found");
		}
		
		List<OrderItemdto> orderitemdto = orderItemPage.getContent().stream().map(entitydtomapper::mapOrderItemTodtoPlusProductAndUser)
				.collect(Collectors.toList());
		
		return Response.builder()
				.status(200).orderItemList(orderitemdto)
				.totalPage(orderItemPage.getTotalPages())
				.totalElement(orderItemPage.getTotalElements()).build();
	}

	

}
