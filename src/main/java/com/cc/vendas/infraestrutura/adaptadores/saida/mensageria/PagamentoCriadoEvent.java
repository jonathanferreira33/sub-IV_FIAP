package com.cc.vendas.infraestrutura.adaptadores.saida.mensageria;

import com.cc.vendas.dominio.pagamento.Pagamento;

import java.math.BigDecimal;
import java.util.UUID;

public record PagamentoCriadoEvent(

        UUID vendaId,
        BigDecimal valor,
        String codigoPagamento

) {

    public static PagamentoCriadoEvent fromDomain(Pagamento pagamento) {

        return new PagamentoCriadoEvent(
                pagamento.getVendaId(),
                pagamento.getValor(),
                pagamento.getCodigoExterno()
        );
    }
}
