package com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.requisicao;

import com.cc.vendas.shared.StatusPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AtualizarStatusVeiculoRequest(

        @Schema(
                description = "Identificador de venda",
                example = "550e8400-e29b-41d4-a716-446655440000"
        )
        @NotNull
        UUID vendaId,
        StatusPagamento statusPagamento
) {
}