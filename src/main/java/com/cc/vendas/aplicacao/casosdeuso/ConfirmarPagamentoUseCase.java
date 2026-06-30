package com.cc.vendas.aplicacao.casosdeuso;

import com.cc.vendas.aplicacao.dto.entrada.ConfirmacaoPagamentoInput;
import com.cc.vendas.aplicacao.dto.entrada.RegistrarPagamentoInput;
import com.cc.vendas.aplicacao.dto.saida.PagamentoOutput;

public interface ConfirmarPagamentoUseCase {
    PagamentoOutput cadastrarPagamento(RegistrarPagamentoInput input);
    void confirmar(ConfirmacaoPagamentoInput input);
}
