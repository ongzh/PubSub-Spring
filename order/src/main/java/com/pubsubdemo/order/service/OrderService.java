package com.pubsubdemo.order.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pubsubdemo.order.model.Order;
import com.pubsubdemo.order.model.OrderRequestDTO;
import com.pubsubdemo.order.pubsub.PublisherConfiguration.OrderRequestGateway;
import com.pubsubdemo.order.repository.OrderRepository;

import javax.transaction.Transactional;

@Service
public class OrderService {

    private static final Log logger = LogFactory.getLog(OrderService.class);
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderRequestGateway orderRequestGateway;

    @Transactional
    public Order createOrder(Order order){
        
        return orderRepository.save(order);
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public void placeOrder(Order order) {
        Order createdOrder = this.createOrder(order);
        orderRequestGateway.sendOrderRequest(new OrderRequestDTO(createdOrder.getId(), createdOrder.getOrderItemDTOs()));
        return;
    }
    
}
