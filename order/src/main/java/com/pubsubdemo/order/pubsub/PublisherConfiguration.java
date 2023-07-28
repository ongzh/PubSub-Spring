package com.pubsubdemo.order.pubsub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageHandler;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import com.pubsubdemo.order.model.OrderRequestDTO;



@Configuration
public class PublisherConfiguration {
    private static final Log logger = LogFactory.getLog(PublisherConfiguration.class);

    @Bean
    public DirectChannel orderRequestOutputChannel(){
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "orderRequestOutputChannel")
    public MessageHandler orderMessageSender(PubSubTemplate pubSubTemplate){
        PubSubMessageHandler adapter = new PubSubMessageHandler(pubSubTemplate, "orderPlaced");
        adapter.setSuccessCallback((ackId, message) -> {
            logger.info("OrderRequest message sent successfully");
        });
        adapter.setFailureCallback((cause, message) -> {
            logger.error("OrderRequest message sent failed");
        });
        return adapter;

    }

    @MessagingGateway
    public interface OrderRequestGateway{
        @Gateway(requestChannel = "orderRequestOutputChannel")
        void sendOrderRequest(OrderRequestDTO orderRequestDTO);
    }
}
