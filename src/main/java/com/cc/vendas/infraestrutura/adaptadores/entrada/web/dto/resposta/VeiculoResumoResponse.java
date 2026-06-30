package com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.resposta;

import com.cc.vendas.dominio.veiculo.StatusVeiculo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record VeiculoResumoResponse(
        @Schema(description = "Identificador único do veículo",
                example = "68784f98-6d6e-492f-bb52-75c2f5cbaf85")
        UUID id,

        @Schema(description = "Marca do veículo",
                example = "Ford")
        String marca,

        @Schema(description = "Modelo do veículo",
                example = "Mustang")
        String modelo,

        String cor,

        @Schema(description = "Ano de fabricação",
                example = "1967")
        Integer ano,

        @Schema(description = "Preço do veículo",
                example = "250000.00")
        BigDecimal preco,

        StatusVeiculo status,

        @Schema(description = "Quando vendido, data exibe data da venda no formato timestamp",
                example = "1970-01-01T00:00Z")
        Instant dataVenda
) {}
