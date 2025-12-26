package com.inventory.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCreatedBy(String createdBy);

}
