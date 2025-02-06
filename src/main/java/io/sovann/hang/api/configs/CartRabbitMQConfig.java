package io.sovann.hang.api.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

@Configuration
public class CartRabbitMQConfig {

    @Value("${rabbitmq.queue.name}")
    public final static String QUEUE_NAME = "cart_queue";
    @Value("${rabbitmq.exchange.name}")
    public final static String EXCHANGE_NAME = "cart_exchange";

    @Bean
    public Queue queue() {
        return new Queue(CartRabbitMQConfig.QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(CartRabbitMQConfig.EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("routing.key.#");
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();

        // Explicitly allow specific packages or classes
        typeMapper.setTrustedPackages("io.sovann.hang.api.features");

        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }
}
