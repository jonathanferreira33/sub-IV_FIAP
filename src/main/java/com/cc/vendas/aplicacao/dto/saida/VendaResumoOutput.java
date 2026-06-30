package com.cc.vendas.aplicacao.dto.saida;

import java.math.BigDecimal;
import java.util.UUID;

public record VendaResumoOutput(
        UUID id,
        UUID idVeiculo,
        BigDecimal preco,
        String docComprador
) {
}
