package com.cc.vendas.aplicacao.dto.mapper;

import com.cc.vendas.aplicacao.dto.saida.PagamentoOutput;
import com.cc.vendas.dominio.pagamento.Pagamento;

public class PagamentoAppMapper {

    public static PagamentoOutput pagamentoParaOutput(Pagamento pagamento) {
        return new PagamentoOutput();
    }
}
