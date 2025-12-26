package com.inventory.order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.order.dto.OrderRequest;
import com.inventory.order.dto.OrderResponse;
import com.inventory.order.model.Order;
import com.inventory.order.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	   private final OrderService service;

	    public OrderController(OrderService service) {
	        this.service = service;
	    }

	    @PostMapping
	    public OrderResponse createOrder(
	            @RequestBody OrderRequest request,
	            @RequestHeader("X-User") String username,
	            @RequestHeader("X-Role") String role
	    ) {

	        Order order = service.createOrder(request, username);

	        OrderResponse response = new OrderResponse();
	        response.setOrderId(order.getId());
	        response.setOrderNumber(order.getOrderNumber());
	        response.setStatus(order.getStatus());

	        return response;
	    }

	    @GetMapping
	    public List<Order> myOrders(
	            @RequestHeader("X-User") String username
	    ) {
	        return service.getOrders(username);
	    }

}
