package com.cc.vendas.infraestrutura.adaptadores.mapper;

import com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.requisicao.AtualizarVeiculoRequest;
import com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.requisicao.RegistrarVeiculoRequest;
import com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.resposta.VeiculoResumoResponse;
import com.cc.vendas.aplicacao.dto.entrada.AtualizarVeiculoInput;
import com.cc.vendas.aplicacao.dto.entrada.RegistrarVeiculoInput;
import com.cc.vendas.aplicacao.dto.saida.VeiculoResumoOutput;

import java.util.List;

public class VeiculoMapperWeb {

    public static RegistrarVeiculoInput registrarVeiculoRequestParaInput(RegistrarVeiculoRequest request) {
        return new RegistrarVeiculoInput(
                request.marca(),
                request.modelo(),
                request.cor(),
                request.ano(),
                request.preco()
        );
    }

    public static AtualizarVeiculoInput atualizarRequestParaAtualizarInput(AtualizarVeiculoRequest request) {
        return new AtualizarVeiculoInput(
                request.marca(),
                request.modelo(),
                request.cor(),
                request.ano(),
                request.preco()
        );
    }

    public static VeiculoResumoResponse resumoOutputParaResponse(VeiculoResumoOutput output) {
        return new VeiculoResumoResponse(
                output.id(),
                output.marca(),
                output.modelo(),
                output.cor(),
                output.ano(),
                output.preco(),
                output.statusVeiculo(),
                output.dataVenda()
        );
    }

    public static List<VeiculoResumoResponse> listaResumoOutputParaResponse(List<VeiculoResumoOutput> outputs) {
        return outputs.stream()
                .map(VeiculoMapperWeb::resumoOutputParaResponse)
                .toList();
    }
}
