package com.inventory.order.dto;

import lombok.Data;

@Data
public class OrderResponse {
	private Long orderId;
    private String orderNumber;
    private String status;

}
