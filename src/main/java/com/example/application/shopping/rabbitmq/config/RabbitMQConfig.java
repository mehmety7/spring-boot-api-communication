package com.example.application.shopping.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_SHOPPING = "shopping-queue";
    public static final String EXCHANGE_SHOPPING = "shopping-exchange";
    public static final String ROUTING_KEY_SHOPPING = "shopping.#";

    public static final String QUEUE_NOTIFICATIONS = "notifications-queue";
    public static final String ROUTING_KEY_NOTIFICATIONS = "shopping.notification";

    @Bean
    public Queue shoppingQueue() {
        return new Queue(QUEUE_SHOPPING, true);
    }

    @Bean
    public Queue notificationsQueue() {
        return new Queue(QUEUE_NOTIFICATIONS, true);
    }

    @Bean
    public TopicExchange shoppingExchange() {
        return new TopicExchange(EXCHANGE_SHOPPING);
    }

    @Bean
    public Binding shoppingBinding(Queue shoppingQueue, TopicExchange shoppingExchange) {
        return BindingBuilder.bind(shoppingQueue).to(shoppingExchange).with(ROUTING_KEY_SHOPPING);
    }

    @Bean
    public Binding notificationsBinding(Queue notificationsQueue, TopicExchange shoppingExchange) {
        return BindingBuilder.bind(notificationsQueue).to(shoppingExchange).with(ROUTING_KEY_NOTIFICATIONS);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    @Qualifier("customRabbitTemplate")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}