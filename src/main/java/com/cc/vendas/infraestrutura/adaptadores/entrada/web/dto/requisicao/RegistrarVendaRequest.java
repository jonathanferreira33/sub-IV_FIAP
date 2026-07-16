package com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.requisicao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record RegistrarVendaRequest(
        @Schema(description = "Marca do veículo", example = "Ford")
        @NotBlank
        String docComprador
) {
}
