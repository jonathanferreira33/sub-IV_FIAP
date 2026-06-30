package com.cc.vendas.infraestrutura.adaptadores.entrada.web.controller;

import com.cc.vendas.aplicacao.casosdeuso.ConfirmarPagamentoUseCase;
import com.cc.vendas.dominio.excecao.RegraNegocioException;
import com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.requisicao.WebhookPagementoRequest;
import com.cc.vendas.shared.StatusPagamento;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WebhookPagamentoController.class)
class WebhookPagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ConfirmarPagamentoUseCase useCase;

    private final UUID vendaId = UUID.randomUUID();
    private final BigDecimal valor = BigDecimal.valueOf(150.00);
    private final String codigoPagamento = "PAY-999AA";
    private final StatusPagamento statusPagamento = StatusPagamento.CONFIRMADO;

    @Test
    void deveProcessarWebhookComSucessoERetornarStatus200() throws Exception {
        WebhookPagementoRequest request = new WebhookPagementoRequest(
                vendaId,
                valor,
                codigoPagamento,
                statusPagamento
        );

        doNothing().when(useCase).confirmar(any());

        mockMvc.perform(post("/webhook/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarStatus422QuandoCapturarRegraNegocioException() throws Exception {
        WebhookPagementoRequest request = new WebhookPagementoRequest(vendaId, valor, codigoPagamento, statusPagamento);

        doThrow(new RegraNegocioException("Transição de status inválida"))
                .when(useCase).confirmar(any());

        mockMvc.perform(post("/webhook/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity());
    }
}