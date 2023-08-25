package com.pubsubdemo.order.pubsub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.pubsubdemo.order.model.OrderResponseDTO;
import com.pubsubdemo.order.service.OrderService;



@Configuration
public class SubscriberConfiguration {
    private static final Log logger = LogFactory.getLog(SubscriberConfiguration.class);

    @Autowired
    OrderService orderService;

    @Bean
    public DirectChannel orderConfirmedInputChannel(){
        return new DirectChannel();
    
    }

    @Bean
    public DirectChannel orderFailedInputChannel(){
        return new DirectChannel();
    
    }

    @Bean 
    public PubSubInboundChannelAdapter orderConfirmedChannelAdapter(
        @Qualifier("orderConfirmedInputChannel") MessageChannel inputChannel,
        PubSubTemplate pubSubtemplate
    ){
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubtemplate, "orderConfirmed-sub");
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.MANUAL);
        adapter.setPayloadType(OrderResponseDTO.class);
        return adapter;
    }

    @Bean 
    public PubSubInboundChannelAdapter orderFailedChannelAdapter(
        @Qualifier("orderFailedInputChannel") MessageChannel inputChannel,
        PubSubTemplate pubSubtemplate
    ){
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubtemplate, "orderFailed-sub");
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.MANUAL);
        adapter.setPayloadType(OrderResponseDTO.class);
        return adapter;
    }

    @ServiceActivator(inputChannel = "orderConfirmedInputChannel")
    void processOrderConfirmedResponse(@Payload OrderResponseDTO orderResponseDTO, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message
    ){
        logger.info("Message Arrived, Order Confirmed: " + orderResponseDTO);
        orderService.processOrderConfirmed(orderResponseDTO);
        message.ack();
    }

    @ServiceActivator(inputChannel = "orderFailedInputChannel")
    void processOrderFailedResponse(@Payload OrderResponseDTO orderResponseDTO, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) 
    BasicAcknowledgeablePubsubMessage message
    ){
        logger.info("Message Arrived, Order Confirmed: " + orderResponseDTO);
        orderService.processOrderFailed(orderResponseDTO);
        message.ack();
    }


    
}
