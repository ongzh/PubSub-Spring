package com.pubsubdemo.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pubsubdemo.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    
}
