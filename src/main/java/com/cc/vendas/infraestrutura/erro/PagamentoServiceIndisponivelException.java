package com.cc.vendas.infraestrutura.erro;

import java.util.UUID;

public class PagamentoServiceIndisponivelException extends RuntimeException {

    public PagamentoServiceIndisponivelException(UUID idPagamento, Throwable cause) {
        super(
                String.format(
                        "Não foi possível consultar o pagamento %s porque o serviço de pagamentos está indisponível.",
                        idPagamento
                ),
                cause
        );
    }
}