package com.cc.vendas.dominio.veiculo;

import com.cc.vendas.dominio.excecao.RegraNegocioException;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status possível de um veículo")
public enum StatusVeiculo {

    @Schema(description = "Veículo em análise interna")
    ANALISE,

    @Schema(description = "Veículo disponível para venda")
    DISPONIVEL_PARA_VENDA,

    @Schema(description = "Veículo já vendido")
    VENDIDO;

    public static StatusVeiculo fromString(String status) {
        if (status == null || status.isBlank()) {
            throw new RegraNegocioException("Status do veículo não informado");
        }

        return switch (status.trim().toLowerCase()) {
            case "analise" -> ANALISE;
            case "disponivel_para_venda", "disponivel", "disponível" -> DISPONIVEL_PARA_VENDA;
            case "vendido" -> VENDIDO;
            default -> throw new RegraNegocioException(
                    "Status do veículo inválido: " + status
            );
        };
    }
}
