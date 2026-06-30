package com.cc.vendas.aplicacao.dto.saida;

import java.util.UUID;

public record VendaResumoOutput(
        UUID id,
        UUID idVeiculo,
        Double preco,
        String docComprador
) {
}
