package com.cc.vendas.infraestrutura.adaptadores.saida.mensageria;

import com.cc.vendas.aplicacao.casosdeuso.VendaEventPublisher;
import com.cc.vendas.infraestrutura.configuracao.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitVendaEventPublisher implements VendaEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publicarVendaRegistrada(PagamentoCriadoEvent evento) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PAYMENT_EXCHANGE,
                RabbitMQConfig.PAYMENT_ROUTING_KEY,
                evento
        );
    }
}
