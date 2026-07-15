package com.cc.vendas.aplicacao.dto.entrada;

import java.util.UUID;

public record AtualizarStatusVeiculoVendidoInput(
        UUID idVeiculo,
        UUID idPagamento
) {

    public void validarInput() {
        if (idVeiculo == null) {
            throw new IllegalArgumentException("Id do veículo obrigatório");
        }

        if (idPagamento == null) {
            throw new IllegalArgumentException("Id do pagamento obrigatório");
        }
    }
}
