package com.cc.vendas.aplicacao.dto.entrada;

import java.math.BigDecimal;
import java.util.UUID;

public record RegistrarPagamentoInput(
        UUID vendaId,
        BigDecimal valor,
        String codigoPagamento
) {
}
