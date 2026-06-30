package com.cc.vendas.shared;

import java.util.List;

public enum StatusPagamento {
    PENDENTE,
    CONFIRMADO,
    CANCELADO;

    public boolean podeTransicionar(StatusPagamento novoStatus) {
        return switch (this) {
            case PENDENTE -> List.of(CONFIRMADO, CANCELADO ).contains(novoStatus);
            case CONFIRMADO, CANCELADO -> false;
        };
    }
}
