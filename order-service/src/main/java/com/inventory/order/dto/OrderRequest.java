package com.inventory.order.dto;


import lombok.Data;

@Data
public class OrderRequest {
	private String itemCode;
	private String itemName;
    private int quantity;
    private double price;

}
