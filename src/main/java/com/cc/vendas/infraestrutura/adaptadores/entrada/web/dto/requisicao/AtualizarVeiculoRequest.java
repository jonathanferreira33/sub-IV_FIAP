package com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.requisicao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Objeto de requisição para atualizar dados de um veículo")
public record AtualizarVeiculoRequest(
        @Schema(description = "Marca do veículo", example = "Ford")
        @NotBlank String marca,

        @Schema(description = "Modelo do veículo", example = "Mustang GT")
        @NotBlank String modelo,

        @Schema(description = "Cor do veículo", example = "Vermelho")
        @NotBlank String cor,

        @Schema(description = "Ano de fabricação", example = "1969")
        @NotNull Integer ano,

        @Schema(description = "Preço de venda", example = "250000.00")
        @Positive Double preco
) {
}
