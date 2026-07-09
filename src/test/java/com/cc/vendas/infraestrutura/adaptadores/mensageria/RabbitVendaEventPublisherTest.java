package com.cc.vendas.infraestrutura.adaptadores.mensageria;

import com.cc.vendas.dominio.pagamento.Pagamento;
import com.cc.vendas.infraestrutura.adaptadores.saida.mensageria.PagamentoCriadoEvent;
import com.cc.vendas.infraestrutura.adaptadores.saida.mensageria.RabbitVendaEventPublisher;
import com.cc.vendas.infraestrutura.configuracao.RabbitMQConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RabbitVendaEventPublisherTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    private RabbitVendaEventPublisher publisher;

    @BeforeEach
    void setUp() {
        publisher = new RabbitVendaEventPublisher(rabbitTemplate);
    }

    @Test
    void devePublicarEventoDeVenda() {

        Pagamento pagamento = Pagamento.criar(
                UUID.randomUUID(),
                BigDecimal.valueOf(100),
                "PIX123"
        );

        PagamentoCriadoEvent evento = PagamentoCriadoEvent.fromDomain(pagamento);

        publisher.publicarVendaRegistrada(evento);

        verify(rabbitTemplate).convertAndSend(
                RabbitMQConfig.VENDA_EXCHANGE,
                RabbitMQConfig.VENDA_ROUTING_KEY,
                evento
        );
    }
}
