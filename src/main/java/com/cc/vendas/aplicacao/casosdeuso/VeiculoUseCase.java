package com.cc.vendas.aplicacao.casosdeuso;

import com.cc.vendas.aplicacao.dto.entrada.AtualizarStatusVeiculoVendidoInput;
import com.cc.vendas.aplicacao.dto.entrada.AtualizarVeiculoInput;
import com.cc.vendas.aplicacao.dto.entrada.RegistrarVeiculoInput;
import com.cc.vendas.aplicacao.dto.saida.VeiculoResumoOutput;

import java.util.List;
import java.util.UUID;

public interface VeiculoUseCase {
    VeiculoResumoOutput atualizarDadosVeiculo(UUID id, AtualizarVeiculoInput veiculo);
    List<VeiculoResumoOutput> buscarVeiculosDisponiveis();
    List<VeiculoResumoOutput> buscarVeiculosVendidos();
    VeiculoResumoOutput buscarVeiculoPorId(UUID id);
    VeiculoResumoOutput cadastrarVeiculo(RegistrarVeiculoInput input);
    void atualizarStatusVeiculoVendido(UUID idVeiculo, AtualizarStatusVeiculoVendidoInput input);
}
