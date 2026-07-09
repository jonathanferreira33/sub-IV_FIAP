package com.cc.vendas.aplicacao.casosdeuso;

import com.cc.vendas.dominio.pagamento.Pagamento;
import com.cc.vendas.infraestrutura.adaptadores.saida.mensageria.PagamentoCriadoEvent;

public interface VendaEventPublisher {
    void publicarVendaRegistrada(PagamentoCriadoEvent evento);
}
