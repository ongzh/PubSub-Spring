package com.pubsubdemo.inventory.pubsub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import com.pubsubdemo.inventory.model.OrderResponseDTO;


@Configuration
public class PublisherConfiguration {
    private static final Log logger = LogFactory.getLog(PublisherConfiguration.class);
    
    @Bean
    public DirectChannel orderConfirmedOutputChannel(){
        return new DirectChannel();
    }

    @Bean
    public DirectChannel orderFailedOutputChannel(){
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "orderConfirmedOutputChannel")
    public PubSubMessageHandler confirmationMessageSender(PubSubTemplate pubSubTemplate){
        PubSubMessageHandler adapter = new PubSubMessageHandler(pubSubTemplate, "orderConfirmed");
        adapter.setSuccessCallback((ackId, message) -> {
            logger.info("OrderConfirmation message sent successfully");
        });
        adapter.setFailureCallback((cause, message) -> {
            logger.error("OrderConfirmation message sent failed");
        });
        return adapter;

    }

    
    @Bean
    @ServiceActivator(inputChannel = "orderFailedOutputChannel")
    public PubSubMessageHandler failedMessageSender(PubSubTemplate pubSubTemplate){
        PubSubMessageHandler adapter = new PubSubMessageHandler(pubSubTemplate, "orderFailed");
        adapter.setSuccessCallback((ackId, message) -> {
            logger.info("OrderFailed message sent successfully");
        });
        adapter.setFailureCallback((cause, message) -> {
            logger.error("OrderFailed message sent failed");
        });
        return adapter;

    }

    
    @MessagingGateway
    public interface OrderConfirmedGateway{
        @Gateway(requestChannel = "orderConfirmedOutputChannel")
        void sendConfirmation(OrderResponseDTO orderResponseDTO);
    }

    @MessagingGateway
    public interface OrderFailedGateway{
        @Gateway(requestChannel = "orderFailedOutputChannel")
        void sendFailure(OrderResponseDTO orderResponseDTO);
    }

    
}
