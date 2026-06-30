package com.cc.vendas.aplicacao.dto.saida;

import com.cc.vendas.dominio.veiculo.StatusVeiculo;

import java.time.Instant;
import java.util.UUID;

public record VeiculoResumoOutput(
        UUID id,
        String marca,
        String modelo,
        String cor,
        Integer ano,
        Double preco,
        StatusVeiculo statusVeiculo,
        Instant dataVenda
) {
}