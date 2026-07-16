package com.cc.vendas.infraestrutura.adaptadores.saida.persistencia.repositorios;

import com.cc.vendas.dominio.veiculo.StatusVeiculo;
import com.cc.vendas.infraestrutura.adaptadores.saida.entidades.JpaVeiculoEntity;
import com.cc.vendas.infraestrutura.adaptadores.saida.mapper.VeiculoJpaMapper;
import com.cc.vendas.dominio.veiculo.Veiculo;
import com.cc.vendas.dominio.veiculo.VeiculoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Veiculo salvar(Veiculo veiculo) {

        Optional<JpaVeiculoEntity> existente = repository.findById(veiculo.getId());

        JpaVeiculoEntity entity;

        if (existente.isPresent()) {
            entity = existente.get();
            entity.setMarca(veiculo.getMarca().valor());
            entity.setModelo(veiculo.getModelo());
            entity.setCor(veiculo.getCor().valor());
            entity.setAno(veiculo.getAno());
            entity.setPreco(veiculo.getPreco());
            entity.setStatusVeiculo(veiculo.getStatus().toString());

            if (veiculo.getStatus() == StatusVeiculo.VENDIDO) {
                entity.setDataVenda(veiculo.getDataVenda());
            }
        } else {
            entity = VeiculoJpaMapper.dominioParaJpa(veiculo);
        }

        JpaVeiculoEntity salva = repository.save(entity);
        return VeiculoJpaMapper.jpaParaDominio(salva);
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
