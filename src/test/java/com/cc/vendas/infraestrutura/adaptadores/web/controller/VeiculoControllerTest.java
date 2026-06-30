package com.cc.vendas.adaptadores.infraestrutura.web.controller;



import com.cc.vendas.aplicacao.casosdeuso.VeiculoUseCase;
import com.cc.vendas.aplicacao.dto.saida.VeiculoResumoOutput;
import com.cc.vendas.infraestrutura.adaptadores.entrada.web.controller.VeiculoController;
import com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.requisicao.AtualizarVeiculoRequest;
import com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.requisicao.RegistrarVeiculoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VeiculoController.class)
class VeiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VeiculoUseCase useCase;

    private final UUID idVeiculo = UUID.randomUUID();
    private final VeiculoResumoOutput veiculoResumoOutput = new VeiculoResumoOutput(
            idVeiculo, "Toyota", "Corolla", "Branco", 2024, BigDecimal.valueOf(100000)
    );

    @Test
    void deveBuscarVeiculosDisponiveisERetornarStatus200() throws Exception {
        List<VeiculoResumoOutput> list = List.of(veiculoResumoOutput);
        when(useCase.buscarVeiculosDisponiveis()).thenReturn(list);

        mockMvc.perform(get("/api/veiculos/disponiveis")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(idVeiculo.toString()))
                .andExpect(jsonPath("$[0].modelo").value("Corolla"));
    }

    @Test
    void deveBuscarVeiculosVendidosERetornarStatus200() throws Exception {
        when(useCase.buscarVeiculosVendidos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/veiculos/vendidos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void deveBuscarVeiculoPorIdERetornarStatus200() throws Exception {
        when(useCase.buscarVeiculoPorId(idVeiculo)).thenReturn(veiculoResumoOutput);

        mockMvc.perform(get("/api/veiculos/{idVeiculo}", idVeiculo)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idVeiculo.toString()))
                .andExpect(jsonPath("$.marca").value("Toyota"));
    }

    @Test
    void deveCadastrarVeiculoERetornarStatus201ComHeaderLocation() throws Exception {
        RegistrarVeiculoRequest request = new RegistrarVeiculoRequest("Toyota", "Corolla", "Branco", 2024, BigDecimal.valueOf(100000));
        when(useCase.cadastrarVeiculo(any())).thenReturn(veiculoResumoOutput);

        mockMvc.perform(post("/api/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/veiculos/" + idVeiculo))
                .andExpect(jsonPath("$.id").value(idVeiculo.toString()));
    }

    @Test
    void deveAtualizarVeiculoERetornarStatus200() throws Exception {
        AtualizarVeiculoRequest request = new AtualizarVeiculoRequest("Toyota", "Corolla Novo", "Preto", 2025, BigDecimal.valueOf(110000));

        VeiculoResumoOutput outputAtualizado = new VeiculoResumoOutput(
                idVeiculo, "Toyota", "Corolla Novo", "Preto", 2025, BigDecimal.valueOf(110000)
        );

        when(useCase.atualizarDadosVeiculo(eq(idVeiculo), any())).thenReturn(outputAtualizado);

        mockMvc.perform(put("/api/veiculos/{idVeiculo}", idVeiculo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value("Corolla Novo"))
                .andExpect(jsonPath("$.cor").value("Preto"));
    }
}