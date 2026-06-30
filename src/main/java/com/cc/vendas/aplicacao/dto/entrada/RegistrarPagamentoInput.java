package com.cc.vendas.aplicacao.dto.entrada;

import java.util.UUID;

public record RegistrarPagamentoInput(
        UUID vendaId,
        Double valor,
        String codigoPagamento
) {
}
