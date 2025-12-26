package com.inventory.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service", url = "http://localhost:8093")
public interface InventoryClient {

    @PostMapping("/inventory/{code}/reduce")
    void reduceStock(@PathVariable("code") String itemCode,
                     @RequestParam("qty") int quantity);
}
