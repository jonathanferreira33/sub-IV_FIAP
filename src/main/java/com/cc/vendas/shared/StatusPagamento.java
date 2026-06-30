package com.cc.vendas.shared;

import java.util.List;

public enum StatusPagamento {
    PENDENTE,
    PAGO,
    CANCELADO;

    public boolean podeTransicionar(StatusPagamento novoStatus) {
        return switch (this) {
            case PENDENTE -> List.of(PAGO, CANCELADO ).contains(novoStatus);
            case PAGO, CANCELADO -> false;
        };
    }
}
