package com.pubsubdemo.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pubsubdemo.order.model.Order;
import com.pubsubdemo.order.model.OrderItem;
import com.pubsubdemo.order.model.OrderStatus;
import com.pubsubdemo.order.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order createOrder(Order order){
        
        return orderRepository.save(order);
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
}
