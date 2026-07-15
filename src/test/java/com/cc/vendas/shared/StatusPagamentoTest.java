package com.cc.vendas.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class StatusPagamentoTest {

    @Test
    void podeTransicionar_DeveRetornarTrue_QuandoPendenteParaConfirmado() {
        boolean resultado = StatusPagamento.PENDENTE.podeTransicionar(StatusPagamento.CONFIRMADO);
        assertTrue(resultado, "PENDENTE deve poder transicionar para CONFIRMADO");
    }

    @Test
    void podeTransicionar_DeveRetornarTrue_QuandoPendenteParaCancelado() {
        boolean resultado = StatusPagamento.PENDENTE.podeTransicionar(StatusPagamento.CANCELADO);
        assertTrue(resultado, "PENDENTE deve poder transicionar para CANCELADO");
    }

    @Test
    void podeTransicionar_DeveRetornarFalse_QuandoPendenteParaEleMesmo() {
        boolean resultado = StatusPagamento.PENDENTE.podeTransicionar(StatusPagamento.PENDENTE);

        assertFalse(resultado, "PENDENTE não deve poder transicionar para PENDENTE novamente");
    }

    @Test
    void podeTransicionar_DeveRetornarFalse_QuandoStatusAtualForConfirmado() {
        assertFalse(StatusPagamento.CONFIRMADO.podeTransicionar(StatusPagamento.PENDENTE));
        assertFalse(StatusPagamento.CONFIRMADO.podeTransicionar(StatusPagamento.CONFIRMADO));
        assertFalse(StatusPagamento.CONFIRMADO.podeTransicionar(StatusPagamento.CANCELADO));
    }

    @Test
    void podeTransicionar_DeveRetornarFalse_QuandoStatusAtualForCancelado() {
        assertFalse(StatusPagamento.CANCELADO.podeTransicionar(StatusPagamento.PENDENTE));
        assertFalse(StatusPagamento.CANCELADO.podeTransicionar(StatusPagamento.CONFIRMADO));
        assertFalse(StatusPagamento.CANCELADO.podeTransicionar(StatusPagamento.CANCELADO));
    }
}
