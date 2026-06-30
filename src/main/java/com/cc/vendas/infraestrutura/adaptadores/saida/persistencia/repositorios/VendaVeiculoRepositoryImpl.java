package com.cc.vendas.infraestrutura.adaptadores.saida.persistencia.repositorios;

import com.cc.vendas.infraestrutura.adaptadores.saida.entidades.JpaVendaVeiculoEntity;
import com.cc.vendas.infraestrutura.adaptadores.saida.mapper.VendaVeiculoJpaMapper;
import com.cc.vendas.dominio.venda.VendaVeiculo;
import com.cc.vendas.dominio.venda.VendaVeiculoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class VendaVeiculoRepositoryImpl implements VendaVeiculoRepository {

    private final JpaVendaVeiculoRepository repository;

    public VendaVeiculoRepositoryImpl(JpaVendaVeiculoRepository repository) {
        this.repository = repository;
    }

    @Override
    public VendaVeiculo salvar(VendaVeiculo vendaVeiculo) {
        JpaVendaVeiculoEntity entity = VendaVeiculoJpaMapper.dominioParaJpa(vendaVeiculo);
        this.repository.save(entity);
        return VendaVeiculoJpaMapper.jpaParaDominio(entity);
    }
}
