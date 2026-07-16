package com.cc.vendas.infraestrutura.adaptadores.saida.persistencia.repositorios;

import com.cc.vendas.infraestrutura.adaptadores.saida.entidades.JpaVeiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaVeiculoRepository extends JpaRepository<JpaVeiculoEntity, UUID> {
    List<JpaVeiculoEntity> findByStatusVeiculoOrderByPrecoAsc(String statusVeiculo);
}
