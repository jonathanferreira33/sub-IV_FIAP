package com.cc.vendas.aplicacao.dto.saida;

import com.cc.vendas.dominio.veiculo.StatusVeiculo;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record VeiculoResumoOutput(
        UUID id,
        String marca,
        String modelo,
        String cor,
        Integer ano,
        BigDecimal preco,
        StatusVeiculo statusVeiculo,
        Instant dataVenda
) {
}