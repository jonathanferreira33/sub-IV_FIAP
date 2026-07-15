package com.cc.vendas.aplicacao.dto.saida;

import com.cc.vendas.shared.StatusPagamento;

import java.util.UUID;

public record PagamentoStatusOutput(
        UUID idPagamento,
        StatusPagamento status
) {
}