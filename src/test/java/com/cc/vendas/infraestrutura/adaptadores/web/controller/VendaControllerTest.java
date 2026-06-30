package com.cc.vendas.infraestrutura.adaptadores.entrada.web.controller;

import com.cc.vendas.aplicacao.casosdeuso.VendaUseCase;
import com.cc.vendas.aplicacao.dto.saida.VeiculoResumoOutput;
import com.cc.vendas.dominio.veiculo.StatusVeiculo;
import com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.requisicao.RegistrarVendaRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VendaController.class)
class VendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private VendaUseCase useCase;

    private final UUID idVeiculo = UUID.randomUUID();
    private final String docComprador = "12345678901";

    @Test
    void deveRegistrarVendaComSucessoERetornarStatus201() throws Exception {

        RegistrarVendaRequest request = new RegistrarVendaRequest(docComprador);

        VeiculoResumoOutput veiculoResumoOutput = new VeiculoResumoOutput(
                idVeiculo,
                "Toyota",
                "Corolla",
                "Branco",
                2024,
                BigDecimal.valueOf(100000),
                StatusVeiculo.DISPONIVEL_PARA_VENDA,
                Instant.now().minus(Duration.ofHours(2))
        );

        when(useCase.registrarVenda(eq(idVeiculo), eq(docComprador)))
                .thenReturn(veiculoResumoOutput);

        mockMvc.perform(post("/api/vendas/{idVeiculo}/registrar-venda", idVeiculo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated()) // Valida se retorna o 201 Created conforme seu código
                .andExpect(header().string("Location", "/api/veiculos/" + idVeiculo)) // Valida o header URI
                .andExpect(jsonPath("$.id").value(idVeiculo.toString()))
                .andExpect(jsonPath("$.modelo").value("Corolla"));
    }
}