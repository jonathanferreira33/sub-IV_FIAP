package com.cc.vendas.aplicacao.dto.entrada;

import java.math.BigDecimal;

public record RegistrarVeiculoInput(
        String marca,
        String modelo,
        String cor,
        Integer ano,
        BigDecimal preco
) {
    public RegistrarVeiculoInput {

        if (marca == null || marca.isBlank()) {
            throw new IllegalArgumentException("Marca é obrigatória");
        }
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("Modelo é obrigatório");
        }
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Preço inválido");
        }
    }
}