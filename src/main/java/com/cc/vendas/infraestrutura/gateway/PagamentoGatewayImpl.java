package com.cc.vendas.infraestrutura.gateway;

import com.cc.vendas.aplicacao.dto.saida.PagamentoStatusOutput;
import com.cc.vendas.aplicacao.gateway.PagamentoGateway;
import com.cc.vendas.infraestrutura.erro.PagamentoServiceIndisponivelException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

@Component
public class PagamentoGatewayImpl implements PagamentoGateway {

    private final RestClient restClient;

    public PagamentoGatewayImpl(
            @Qualifier("pagamentoRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public PagamentoStatusOutput buscarPagamento(UUID idPagamento) {

        try {
            return restClient.get()
                    .uri("/pagamentos/{id}", idPagamento)
                    .retrieve()
                    .body(PagamentoStatusOutput.class);

        } catch (RestClientException ex) {
            throw new PagamentoServiceIndisponivelException(idPagamento, ex);
        }
    }
}