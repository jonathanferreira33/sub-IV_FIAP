package com.cc.vendas.infraestrutura.adaptadores.saida.persistencia.repositorios;

import com.cc.vendas.infraestrutura.adaptadores.saida.entidades.JpaVeiculoEntity;
import com.cc.vendas.infraestrutura.adaptadores.saida.mapper.VeiculoJpaMapper;
import com.cc.vendas.dominio.veiculo.Veiculo;
import com.cc.vendas.dominio.veiculo.VeiculoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class VeiculoRepositoryImpl implements VeiculoRepository {

    private final JpaVeiculoRepository repository;

    public VeiculoRepositoryImpl(JpaVeiculoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Veiculo salvar(Veiculo veiculo) {
        JpaVeiculoEntity entity = VeiculoJpaMapper.dominioParaJpa(veiculo);
        this.repository.save(entity);
        return VeiculoJpaMapper.jpaParaDominio(entity);
    }

    @Override
    public Optional<Veiculo> buscarPorId(UUID id) {
        return repository.findById(id)
                .map(VeiculoJpaMapper::jpaParaDominio);
    }

    @Override
    public List<Veiculo> buscarTodosVeiculosPorStatusOrdenadoPorPreco(String status) {
        return repository.findByStatusVeiculoOrderByPrecoAsc(status)
                .stream()
                .map(VeiculoJpaMapper::jpaParaDominio)
                .toList();
    }
}
