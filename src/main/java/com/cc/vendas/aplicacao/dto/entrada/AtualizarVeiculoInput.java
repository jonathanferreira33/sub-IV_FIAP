package com.cc.vendas.aplicacao.dto.entrada;

public record AtualizarVeiculoInput(
        String marca,
        String modelo,
        String cor,
        Integer ano,
        Double preco
) {
}
