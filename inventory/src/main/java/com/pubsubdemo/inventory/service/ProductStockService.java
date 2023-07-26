package com.pubsubdemo.inventory.service;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pubsubdemo.inventory.model.OrderRequestDTO;
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
    public ProductStock updateProductStockByProductId(Long productId, Integer quantity) {
        ProductStock productStock = productStockRepository.findByProductId(productId);
        if (productStock == null) {
            throw new IllegalArgumentException("Product not found");
        }
        productStock.setQuantity(quantity);
        return productStockRepository.save(productStock);
    }

    public int getProductStockCount(Long productStockId){
        ProductStock productStock = productStockRepository.findById(productStockId).orElse(null);
        if (productStock == null) {
            throw new IllegalArgumentException("ProductStock not found");
        }
        return productStock.getQuantity();
    }

    @Transactional
    public ProductStock createProductStock(ProductStock productStock) {
        return productStockRepository.save(productStock);
    }

    @Transactional
    public void processOrderRequest(OrderRequestDTO orderRequestDTO) {
        logger.info("Processing order request: " + orderRequestDTO);
    }

}
