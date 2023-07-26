package com.pubsubdemo.inventory.pubsub;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.AckMode;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import com.pubsubdemo.inventory.model.OrderRequestDTO;
import com.pubsubdemo.inventory.service.ProductStockService;


@Configuration
public class SubscriberConfiguration {
    private static final Log logger = LogFactory.getLog(SubscriberConfiguration.class);


    @Autowired
    ProductStockService productStockService;
    
    @Bean
    public DirectChannel orderRequestInputChannel(){
        return new DirectChannel();
    }
    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
        @Qualifier("orderRequestInputChannel") MessageChannel inputChannel,    
        PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "orderRequestSubscription");
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.MANUAL);
        adapter.setPayloadType(OrderRequestDTO.class);
        return adapter;
    }

    @ServiceActivator(inputChannel = "orderRequestInputChannel")
    public void processOrderRequest(@Payload OrderRequestDTO orderRequestDTO, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        logger.info("Received order request: " + orderRequestDTO);
        try {
            productStockService.processOrderRequest(orderRequestDTO);
            message.ack();
        } catch (Exception e) {
            logger.error("Error processing order request: " + orderRequestDTO);
            message.nack();
        }
    }

}
