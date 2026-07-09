package com.cc.vendas.infraestrutura.configuracao;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String VENDA_EXCHANGE = "venda.exchange";
    public static final String VENDA_QUEUE = "venda.queue";
    public static final String VENDA_ROUTING_KEY = "venda.registrada";

    @Bean
    public DirectExchange vendaExchange() {
        return new DirectExchange(VENDA_EXCHANGE);
    }

    @Bean
    public Queue vendaQueue() {
        return new Queue(VENDA_QUEUE, true);
    }

    @Bean
    public Binding vendaBinding() {
        return BindingBuilder
                .bind(vendaQueue())
                .to(vendaExchange())
                .with(VENDA_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter converter) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }
}
