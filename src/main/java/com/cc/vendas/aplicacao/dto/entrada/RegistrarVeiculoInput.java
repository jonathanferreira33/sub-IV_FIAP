package com.cc.vendas.aplicacao.dto.entrada;

public record RegistrarVeiculoInput(
        String marca,
        String modelo,
        String cor,
        Integer ano,
        Double preco
) {
    public RegistrarVeiculoInput {

        if (marca == null || marca.isBlank()) {
            throw new IllegalArgumentException("Marca é obrigatória");
        }
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("Modelo é obrigatório");
        }
        if (preco == null || preco <= 0) {
            throw new IllegalArgumentException("Preço inválido");
        }
    }
}