package com.pubsubdemo.inventory.service;
import java.util.ArrayList;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pubsubdemo.inventory.model.OrderItemDTO;
import com.pubsubdemo.inventory.model.OrderRequestDTO;
import com.pubsubdemo.inventory.model.OrderResponseDTO;
import com.pubsubdemo.inventory.model.ProductStock;
import com.pubsubdemo.inventory.pubsub.PublisherConfiguration.OrderConfirmedGateway;
import com.pubsubdemo.inventory.pubsub.PublisherConfiguration.OrderFailedGateway;
import com.pubsubdemo.inventory.repository.ProductStockRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



@Service
public class ProductStockService {
    
    private static final Log logger = LogFactory.getLog(ProductStockService.class);


    @Autowired
    private ProductStockRepository productStockRepository;
    
    @Autowired
    private OrderConfirmedGateway orderConfirmedGateway;

    @Autowired
    private OrderFailedGateway orderFailedGateway;

    

    @Transactional
    public void processOrderRequest(OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId(orderRequestDTO.getId());
        logger.info("Processing order request: " + orderRequestDTO);
        //check if order can be placed
        ArrayList<String> outOfStocks = new ArrayList<String>();

        for (OrderItemDTO orderItem: orderRequestDTO.getOrderItems()){
            if (!this.checkProductStock(orderItem)){
                outOfStocks.add(orderItem.getProductId().toString());
            }
        };

        if (!outOfStocks.isEmpty()){
            orderResponseDTO.setSuccessful(false);
                orderResponseDTO.setSuccessful(false);
                orderResponseDTO.setMessage("Out of stock for product ids: " + String.join(",", outOfStocks));
                orderFailedGateway.sendFailure(orderResponseDTO);
                logger.info("Order failed: " + orderResponseDTO + " sent to pubsub");
                return;
        }

        for (OrderItemDTO orderItem: orderRequestDTO.getOrderItems()){
            this.updateProductStock(orderItem);
        };

        orderResponseDTO.setSuccessful(true);
        orderResponseDTO.setMessage("Order:" + orderResponseDTO.getOrderId() + " placed successfully");
        orderConfirmedGateway.sendConfirmation(orderResponseDTO);
        logger.info("Order confirmed: " + orderResponseDTO + " sent to pubsub");
    }


    public int getProductStockCount(Long productStockId){
        ProductStock productStock = productStockRepository.findById(productStockId).orElse(null);
        if (productStock == null) {
            logger.error("No such product: " + productStockId);
            throw new IllegalArgumentException("ProductStock with id: " +  productStockId + " not found");
        }
        return productStock.getQuantity();
    }

    public boolean checkProductStock(OrderItemDTO orderItem) {
        ProductStock productStock = productStockRepository.findByProductId(orderItem.getProductId());
        return productStock.getQuantity() >= orderItem.getQuantity();
    }

    
    @Transactional
    public int updateProductStock(OrderItemDTO orderItem) {
        ProductStock productStock = productStockRepository.findByProductId(orderItem.getProductId());
        int newQuantity = productStock.getQuantity() - orderItem.getQuantity();
        productStock.setQuantity(newQuantity);
        productStockRepository.save(productStock);
        return newQuantity;
    }

    @Transactional
    public ProductStock createProductStock(ProductStock productStock) {
        return productStockRepository.save(productStock);
    }


    public ProductStock updateProductStockByProductId(Long productId, int updatedQty) {
        ProductStock productStock = productStockRepository.findByProductId(productId);
        if (productStock == null) {
            logger.error("No such product: " + productId);
            throw new IllegalArgumentException("ProductStock with id: " +  productId + " not found");
        }
        productStock.setQuantity(updatedQty);
        return productStockRepository.save(productStock);
    }



}
