package com.inventory.inventory.dto;

import lombok.Data;

@Data
public class InventoryRequest {
	
    private String itemCode;
    private String itemName;
    private int quantity;
    private double price;

    // getters & setters
}

