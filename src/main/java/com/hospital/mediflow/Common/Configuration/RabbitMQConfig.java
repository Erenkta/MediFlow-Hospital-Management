package com.hospital.mediflow.Common.Configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE = "notification.exchange";
    public static final String QUEUE = "notification.queue";
    public static final String ROUTING_KEY = "notification.routing.key"; // address to send the message

    @Bean
    public DirectExchange exchange(){return new DirectExchange(EXCHANGE);} // First stop of the message

    @Bean
    public Queue queue(){return new Queue(QUEUE);} // Where the messages are stored.

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange){ // to bind the message storage and direct exchange
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
