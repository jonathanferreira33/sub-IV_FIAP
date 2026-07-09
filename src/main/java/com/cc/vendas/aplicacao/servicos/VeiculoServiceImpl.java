package com.cc.vendas.aplicacao.servicos;

import com.cc.vendas.aplicacao.casosdeuso.VeiculoUseCase;
import com.cc.vendas.aplicacao.dto.entrada.AtualizarVeiculoInput;
import com.cc.vendas.aplicacao.dto.entrada.RegistrarVeiculoInput;
import com.cc.vendas.aplicacao.dto.mapper.VeiculoAppMapper;
import com.cc.vendas.aplicacao.dto.saida.VeiculoResumoOutput;
import com.cc.vendas.dominio.excecao.RegraNegocioException;
import com.cc.vendas.dominio.veiculo.StatusVeiculo;
import com.cc.vendas.dominio.veiculo.Veiculo;
import com.cc.vendas.dominio.veiculo.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VeiculoServiceImpl implements VeiculoUseCase {

    private final VeiculoRepository repository;

    public VeiculoServiceImpl(VeiculoRepository repository) {
        this.repository = repository;
    }

    @Override
    public VeiculoResumoOutput atualizarDadosVeiculo(UUID id, AtualizarVeiculoInput veiculo) {
        Veiculo veiculoEntity = repository.buscarPorId(id)
                .orElseThrow(() ->  new EntityNotFoundException("Veículo não encontrado"));

        veiculoEntity.atualizarDados(
                veiculo.marca(),
                veiculo.modelo(),
                veiculo.cor(),
                veiculo.ano(),
                veiculo.preco()
        );


        repository.salvar(veiculoEntity);

        return VeiculoAppMapper.veiculoParaResumoOutput(veiculoEntity);
    }

    @Override
    public List<VeiculoResumoOutput> buscarVeiculosDisponiveis() {
        return repository.buscarTodosVeiculosPorStatusOrdenadoPorPreco(StatusVeiculo.DISPONIVEL_PARA_VENDA.name())
                .stream()
                .map(VeiculoAppMapper::veiculoParaResumoOutput)
                .toList();
    }

    @Override
    public List<VeiculoResumoOutput> buscarVeiculosVendidos() {
        return repository.buscarTodosVeiculosPorStatusOrdenadoPorPreco(StatusVeiculo.VENDIDO.name())
                .stream()
                .map(VeiculoAppMapper::veiculoParaResumoOutput)
                .toList();
    }

    @Override
    public VeiculoResumoOutput buscarVeiculoPorId(UUID id) {
        return repository.buscarPorId(id)
                .map(VeiculoAppMapper::veiculoParaResumoOutput)
                .orElseThrow(() ->
                        new EntityNotFoundException("Veiculo não encontrado"));
    }

    @Override
    public VeiculoResumoOutput cadastrarVeiculo(RegistrarVeiculoInput input) {

        validarAno(input.ano());

        Veiculo veiculo = Veiculo.criar(
                input.marca(),
                input.modelo(),
                input.cor(),
                input.ano(),
                input.preco()
        );

        veiculo.alterarStatusParaDisponivel();

        repository.salvar(veiculo);

        return VeiculoAppMapper.veiculoParaResumoOutput(veiculo);
    }

    private void validarAno(int ano) {
        int primeiroCarro = 1886;
        int anoAtual = LocalDateTime.now().getYear();
        if (ano < primeiroCarro || ano > anoAtual ) {
            throw new RegraNegocioException("Ano inválido");
        }
    }
}
