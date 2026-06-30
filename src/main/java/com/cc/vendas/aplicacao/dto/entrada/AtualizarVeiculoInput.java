package com.cc.vendas.aplicacao.dto.entrada;

import java.math.BigDecimal;

public record AtualizarVeiculoInput(
        String marca,
        String modelo,
        String cor,
        Integer ano,
        BigDecimal preco
) {
}
