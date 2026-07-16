package com.cc.vendas.aplicacao.dto.entrada;

import com.cc.vendas.shared.StatusPagamento;

import java.util.UUID;

public record AtualizarStatusVeiculoVendidoInput(
        UUID idVenda,
        StatusPagamento statusPagamento
) {

    public void validarInput() {
        if (idVenda == null) {
            throw new IllegalArgumentException("Id venda do veículo obrigatório");
        }

        if (statusPagamento == null) {
            throw new IllegalArgumentException("Status pagamento obrigatório");
        }
    }
}
