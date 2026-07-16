package com.cc.vendas.infraestrutura.adaptadores.entrada.web.controller;

import com.cc.vendas.aplicacao.dto.saida.VeiculoResumoOutput;
import com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.requisicao.AtualizarStatusVeiculoRequest;
import com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.requisicao.AtualizarVeiculoRequest;
import com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.requisicao.RegistrarVeiculoRequest;
import com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.resposta.VeiculoResumoResponse;
import com.cc.vendas.infraestrutura.adaptadores.mapper.VeiculoMapperWeb;
import com.cc.vendas.aplicacao.casosdeuso.VeiculoUseCase;

import com.cc.vendas.infraestrutura.erro.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/veiculos")
@Tag(name = "Veiculo", description = "Operações relacionadas manipulação de dados de veículos")
public class VeiculoController {

    private final VeiculoUseCase useCase;
    private static final Logger log = LoggerFactory.getLogger(VeiculoController.class);

    public VeiculoController(VeiculoUseCase useCase) {
        this.useCase = useCase;
    }

    @Operation(
            summary = "Listar veículos disponiveis",
            description = "Retorna a lista de veículos disponiveis. Pode retornar lista vazia."
    )
    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de veículos disponiveis retornada com sucesso (pode ser vazia)",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            )
    })
    @GetMapping("/disponiveis")
    public ResponseEntity<List<VeiculoResumoResponse>> buscarVeiculosDisponiveis () {

        var output = VeiculoMapperWeb.listaResumoOutputParaResponse(
                useCase.buscarVeiculosDisponiveis());

        return ResponseEntity.ok(output);
    }

    @Operation(
            summary = "Listar veículos vendidos",
            description = "Retorna a lista de veículos vendidos. Pode retornar lista vazia."
    )
    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de veículos vendidos retornada com sucesso (pode ser vazia)",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            )
    })
    @GetMapping("/vendidos")
    public ResponseEntity<List<VeiculoResumoResponse>> buscarVeiculosVendidos () {

        var output = VeiculoMapperWeb.listaResumoOutputParaResponse(useCase.buscarVeiculosVendidos());
        return ResponseEntity.ok(output);
    }

    @Operation(
            summary = "Buscar veículo por ID",
            description = "Retorna os dados resumidos de um veículo pelo seu identificador único."
    )
    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Veículo encontrado com sucesso",
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
                    responseCode = "400",
                    description = "ID inválido",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            )
    })
    @GetMapping("/{idVeiculo}")
    public ResponseEntity<VeiculoResumoResponse> buscarVeiculo (
            @Parameter(
                    description = "ID do veículo",
                    example = "123e4567-e89b-12d3-a456-426614174000",
                    required = true
            )
            @PathVariable UUID idVeiculo) {
        VeiculoResumoOutput output = useCase.buscarVeiculoPorId(idVeiculo);

        return ResponseEntity.ok(
                VeiculoMapperWeb.resumoOutputParaResponse(output)
        );
    }

    @Operation(
            summary = "Registrar cadastro de um veículo",
            description = "Registra o cadastro de um veículo informando"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Veículo registrada com sucesso",
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
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<VeiculoResumoResponse> cadastrar(@RequestBody RegistrarVeiculoRequest veiculoRequest) {
        var output = useCase.cadastrarVeiculo(
                VeiculoMapperWeb.registrarVeiculoRequestParaInput(veiculoRequest)
        );

        var response = VeiculoMapperWeb.resumoOutputParaResponse(output);

        return ResponseEntity
                .created(URI.create("/api/veiculos/" + response.id()))
                .body(response);
    }

    @Operation(
            summary = "Atualizar veículo",
            description = "Atualiza os dados de um veículo existente pelo seu ID."
    )
    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Veículo atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida (dados malformados ou inválidos)",
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
                    description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            )
    })
    @PutMapping("/{idVeiculo}")
    public ResponseEntity<VeiculoResumoResponse> atualizar(
            @Parameter(
                    description = "ID do veículo",
                    example = "123e4567-e89b-12d3-a456-426614174000",
                    required = true
            )
            @PathVariable UUID idVeiculo,
            @Valid
            @RequestBody AtualizarVeiculoRequest request) {

        var output = useCase.atualizarDadosVeiculo(
                idVeiculo,
                VeiculoMapperWeb.atualizarRequestParaAtualizarInput(request)
        );

        var response = VeiculoMapperWeb.resumoOutputParaResponse(output);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Atualizar status do veículo",
            description = "Atualiza o status do veículo após confirmação do pagamento."
    )
    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Status atualizado com sucesso"
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida",
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
                    description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            )
    })
    @PatchMapping("/notificar-venda/{idVeiculo}")
    public ResponseEntity<Void> notificarVenda(

            @Parameter(
                    description = "ID do veículo",
                    example = "123e4567-e89b-12d3-a456-426614174000",
                    required = true
            )
            @PathVariable UUID idVeiculo,

            @Valid
            @RequestBody AtualizarStatusVeiculoRequest request) {

        log.info("Id veiculo: " + idVeiculo + "| VendaID: " + request.vendaId());
        useCase.atualizarStatusVeiculoVendido(
                idVeiculo,
                VeiculoMapperWeb.requestParaInput(request)
        );

        return ResponseEntity.ok().build();
    }

}
