package com.inventory.order.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.inventory.order.common.OrderCreatedEvent;
import com.inventory.order.dto.OrderRequest;
import com.inventory.order.feign.InventoryClient;
import com.inventory.order.kafka.OrderEventProducer;
import com.inventory.order.model.Order;
import com.inventory.order.repository.OrderRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class OrderService {

    private final OrderRepository repo;
    private final InventoryClient inventoryClient;
    private final OrderEventProducer orderEventProducer;

    public OrderService(
            OrderRepository repo,
            InventoryClient inventoryClient,
            OrderEventProducer orderEventProducer) {

        this.repo = repo;
        this.inventoryClient = inventoryClient;
        this.orderEventProducer = orderEventProducer;
    }

    @CircuitBreaker(name = "inventoryService", fallbackMethod = "inventoryFallback")
    @Retry(name = "inventoryService")
    public Order createOrder(OrderRequest req, String username) {

        // 1️⃣ Reduce inventory (SYNC)
        inventoryClient.reduceStock(
                req.getItemCode(),
                req.getQuantity()
        );

        // 2️⃣ Save order
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setItemCode(req.getItemCode());
        order.setItemName(req.getItemName());
        order.setQuantity(req.getQuantity());
        order.setPrice(req.getPrice());
        order.setStatus("PAYMENT_PENDING");
        order.setCreatedBy(username);

        Order saved = repo.save(order);

        // 3️⃣ Publish Kafka event (ASYNC)
        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setOrderNumber(saved.getOrderNumber());
        event.setItemCode(saved.getItemCode());
        event.setQuantity(saved.getQuantity());
        event.setAmount(saved.getPrice() * saved.getQuantity());
        event.setUser(username);

        orderEventProducer.publish(event);

        // 4️⃣ Return saved order
        return saved;
    }

    // 🔁 FALLBACK METHOD
    public Order inventoryFallback(OrderRequest req, String username, Exception ex) {
        throw new RuntimeException(
            "Inventory service unavailable. Please try again later."
        );
    }

    public List<Order> getOrders(String username) {
        return repo.findByCreatedBy(username);
    }
}
