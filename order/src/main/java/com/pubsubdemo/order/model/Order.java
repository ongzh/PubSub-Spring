package com.pubsubdemo.order.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    //ensure OrderItem is persisted when Order is persisted
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> orderItems;
    @Enumerated(EnumType.STRING)
    private OrderStatus OrderStatus;

    public List<OrderItemDTO> getOrderItemDTOs() {
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>(0);
        for (OrderItem orderItem : orderItems) {
            orderItemDTOs.add(new OrderItemDTO(orderItem.getProductId(), orderItem.getQuantity()));
        }

        return orderItemDTOs;
    }
}
