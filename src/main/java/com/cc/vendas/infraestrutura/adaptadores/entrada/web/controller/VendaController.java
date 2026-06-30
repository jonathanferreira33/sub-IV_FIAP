package com.cc.vendas.infraestrutura.adaptadores.entrada.web.controller;

import com.cc.vendas.aplicacao.casosdeuso.VendaUseCase;
import com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.requisicao.RegistrarVendaRequest;
import com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.resposta.VeiculoResumoResponse;
import com.cc.vendas.infraestrutura.adaptadores.web.mapper.VeiculoMapperWeb;
import com.cc.vendas.infraestrutura.erro.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("api/vendas")
@Tag(name = "Vendas", description = "Operações relacionadas à venda de veículos")
public class VendaController {
    private final VendaUseCase useCase;

    public VendaController(VendaUseCase vendaVeiculoUseCase) {
        this.useCase = vendaVeiculoUseCase;
    }

    @Operation(
            summary = "Registrar venda de um veículo",
            description = "Registra a venda de um veículo disponível para venda."
    )
    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "204",
                    description = "Venda registrada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Veículo não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Erro de regra de negócio",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro desconhecido",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            )
    })
    @PostMapping("/{idVeiculo}/registrar-venda")
    public ResponseEntity<VeiculoResumoResponse> registrarVenda(
            @Parameter(
                    description = "ID do veículo",
                    required = true,
                    example = "123j4567-e89b-12j3-a456-426614194000"
            )
            @PathVariable UUID idVeiculo,
            @RequestBody RegistrarVendaRequest request) {

        var output = useCase.registrarVenda(idVeiculo, request.docComprador());

        var response = VeiculoMapperWeb.resumoOutputParaResponse(output);

        return ResponseEntity
                .created(URI.create("/api/veiculos/" + idVeiculo))
                .body(response);
    }

}


