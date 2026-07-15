package com.cc.vendas.infraestrutura.gateway;

import com.cc.vendas.aplicacao.dto.saida.PagamentoStatusOutput;
import com.cc.vendas.infraestrutura.erro.PagamentoServiceIndisponivelException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings({"rawtypes", "unchecked"})
class PagamentoGatewayImplTest {

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    @InjectMocks
    private PagamentoGatewayImpl pagamentoGateway;

    private UUID idPagamento;
    private PagamentoStatusOutput statusOutputMock;

    @BeforeEach
    void setUp() {
        idPagamento = UUID.randomUUID();
        statusOutputMock = mock(PagamentoStatusOutput.class);
    }

    @Test
    void buscarPagamento_DeveRetornarStatus_QuandoRequisicaoForBemSucedida() {
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/pagamentos/{id}", idPagamento)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(PagamentoStatusOutput.class)).thenReturn(statusOutputMock);

        PagamentoStatusOutput resultado = pagamentoGateway.buscarPagamento(idPagamento);

        assertNotNull(resultado);
        assertEquals(statusOutputMock, resultado);

        verify(restClient).get();
        verify(requestHeadersUriSpec).uri("/pagamentos/{id}", idPagamento);
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).body(PagamentoStatusOutput.class);
    }

    @Test
    void buscarPagamento_DeveLancarPagamentoServiceIndisponivelException_QuandoRestClientFalhar() {
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/pagamentos/{id}", idPagamento)).thenReturn(requestHeadersSpec);

        RestClientException erroSimulado = new RestClientException("Erro de conexão");
        when(requestHeadersSpec.retrieve()).thenThrow(erroSimulado);

        PagamentoServiceIndisponivelException exception = assertThrows(
                PagamentoServiceIndisponivelException.class,
                () -> pagamentoGateway.buscarPagamento(idPagamento)
        );

        assertEquals(erroSimulado, exception.getCause());

        verify(responseSpec, never()).body(any(Class.class));
    }
}