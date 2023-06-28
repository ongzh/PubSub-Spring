package com.pubsubdemo.order.model;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequestDTO {
    private Long id;
    private List<OrderItemDTO> orderItems;
}

    
