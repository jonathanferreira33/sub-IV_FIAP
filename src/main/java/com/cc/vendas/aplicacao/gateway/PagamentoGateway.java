package com.cc.vendas.aplicacao.gateway;

import com.cc.vendas.aplicacao.dto.saida.PagamentoStatusOutput;

import java.util.UUID;

public interface PagamentoGateway {

    PagamentoStatusOutput buscarPagamento(UUID idPagamento);

}