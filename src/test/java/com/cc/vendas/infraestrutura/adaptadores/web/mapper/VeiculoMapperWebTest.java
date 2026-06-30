package com.cc.vendas.infraestrutura.adaptadores.web.mapper;

import com.cc.vendas.aplicacao.dto.entrada.AtualizarVeiculoInput;
import com.cc.vendas.aplicacao.dto.entrada.RegistrarVeiculoInput;
import com.cc.vendas.aplicacao.dto.saida.VeiculoResumoOutput;
import com.cc.vendas.dominio.veiculo.StatusVeiculo;
import com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.requisicao.AtualizarVeiculoRequest;
import com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.requisicao.RegistrarVeiculoRequest;
import com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.resposta.VeiculoResumoResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VeiculoMapperWebTest {

    @Test
    void deveMapearRegistrarVeiculoRequestParaInput() {
        RegistrarVeiculoRequest request = new RegistrarVeiculoRequest("Toyota", "Corolla", "Branco", 2024, BigDecimal.TEN);

        RegistrarVeiculoInput input = VeiculoMapperWeb.registrarVeiculoRequestParaInput(request);

        assertNotNull(input);
        assertEquals(request.marca(), input.marca());
        assertEquals(request.modelo(), input.modelo());
    }

    @Test
    void deveMapearAtualizarRequestParaAtualizarInput() {
        AtualizarVeiculoRequest request = new AtualizarVeiculoRequest("Honda", "Civic", "Preto", 2025, BigDecimal.ONE);

        AtualizarVeiculoInput input = VeiculoMapperWeb.atualizarRequestParaAtualizarInput(request);

        assertNotNull(input);
        assertEquals(request.marca(), input.marca());
        assertEquals(request.modelo(), input.modelo());
    }

    @Test
    void deveMapearListaResumoOutputParaResponse() {
        VeiculoResumoOutput output = new VeiculoResumoOutput(
                UUID.randomUUID(),
                "Ford",
                "Ka",
                "Vermelho",
                2020,
                BigDecimal.ZERO,
                StatusVeiculo.DISPONIVEL_PARA_VENDA,
                Instant.now()
        );

        List<VeiculoResumoResponse> responses = VeiculoMapperWeb.listaResumoOutputParaResponse(List.of(output));

        assertFalse(responses.isEmpty());
        assertEquals(output.id(), responses.get(0).id());
        assertEquals(output.modelo(), responses.get(0).modelo());
    }

}
