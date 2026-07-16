package com.cc.vendas.dominio.excecao;

import java.util.UUID;

public class PagamentoNaoAprovadoException extends RegraNegocioException {

    public PagamentoNaoAprovadoException(UUID idPagamento) {
        super("Não é possível concluir a venda, pois o pagamento " + idPagamento + " não foi aprovado.");
    }
}
