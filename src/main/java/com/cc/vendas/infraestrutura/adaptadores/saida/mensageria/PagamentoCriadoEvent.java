package com.cc.vendas.infraestrutura.adaptadores.saida.mensageria;

import com.cc.vendas.dominio.pagamento.Pagamento;
import com.cc.vendas.shared.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.UUID;

public record PagamentoCriadoEvent(
        BigDecimal amount,
        UUID customerId,
        String description,
        UUID vendaId,
        PaymentMethod paymentMethod,
        CardRequest card,
        PixRequest pix,
        String codigoExternoPagamento
) {

    public static PagamentoCriadoEvent fromDomain(Pagamento pagamento) {
        return new PagamentoCriadoEvent(
                pagamento.getValor(),
                pagamento.getCustomerId(),
                "-",
                pagamento.getVendaId(),
                pagamento.getPaymentMethod(),
                null,
                null,
                pagamento.getCodigoExterno()
        );
    }
}
