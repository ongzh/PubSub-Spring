package com.pubsubdemo.order.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pubsubdemo.order.model.Order;
import com.pubsubdemo.order.model.OrderItem;
import com.pubsubdemo.order.model.OrderRequestDTO;
import com.pubsubdemo.order.pubsub.PublisherConfiguration.OrderRequestGateway;
import com.pubsubdemo.order.repository.OrderItemRepository;
import com.pubsubdemo.order.repository.OrderRepository;
import com.pubsubdemo.order.model.OrderStatus;

import javax.transaction.Transactional;

@Service
public class OrderService {

    private static final Log logger = LogFactory.getLog(OrderService.class);
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private OrderRequestGateway orderRequestGateway;

    @Transactional
    private Order createOrder(Order order){
        Order createdOrder = orderRepository.save(order);
        for (OrderItem orderItem: createdOrder.getOrderItems()){
            orderItem.setOrder(order);

        }
        orderItemRepository.saveAll(createdOrder.getOrderItems());
        logger.info("Order with id: " + createdOrder.getId()+ " created, Status: Pending");
        return createdOrder;
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order placeOrder(Order order) {
        Order createdOrder = this.createOrder(order);
        orderRequestGateway.sendOrderRequest(new OrderRequestDTO(createdOrder.getId(), createdOrder.getOrderItemDTOs()));
        return createdOrder;
    }
    
}
