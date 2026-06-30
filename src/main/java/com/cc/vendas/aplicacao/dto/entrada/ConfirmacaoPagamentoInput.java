package com.cc.vendas.aplicacao.dto.entrada;

import com.cc.vendas.shared.StatusPagamento;

import java.math.BigDecimal;
import java.util.UUID;

public record ConfirmacaoPagamentoInput(
        UUID vendaId,
        BigDecimal valor,
        String codigoPagamento,
        StatusPagamento statusPagamento
) {
    public ConfirmacaoPagamentoInput {
        if (codigoPagamento == null || codigoPagamento.isBlank())
            throw new IllegalArgumentException("Código do pagamento é obrigatório");

    }
}
