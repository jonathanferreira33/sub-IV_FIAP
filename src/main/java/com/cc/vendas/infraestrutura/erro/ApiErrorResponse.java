package com.cc.vendas.infraestrutura.erro;

public record ApiErrorResponse(
        String timestamp,
        int status,
        String error,
        String message,
        String path
) {

}
