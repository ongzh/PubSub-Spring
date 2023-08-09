package com.pubsubdemo.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pubsubdemo.order.model.Order;
import com.pubsubdemo.order.service.OrderService;

@RequestMapping
@RestController("/order")
public class OrderController {
    
    @Autowired
    OrderService orderService;

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<Order>> getAllOrders(){
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

  

    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order){

        orderService.placeOrder(order);

        return ResponseEntity.status(HttpStatus.OK).body(order);

    }
    


}
