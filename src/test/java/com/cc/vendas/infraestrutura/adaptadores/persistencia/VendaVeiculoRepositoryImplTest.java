package com.cc.vendas.infraestrutura.adaptadores.persistencia;

import com.cc.vendas.dominio.venda.VendaVeiculo;
import com.cc.vendas.infraestrutura.adaptadores.saida.entidades.JpaVendaVeiculoEntity;
import com.cc.vendas.infraestrutura.adaptadores.saida.persistencia.repositorios.JpaVendaVeiculoRepository;
import com.cc.vendas.infraestrutura.adaptadores.saida.persistencia.repositorios.VendaVeiculoRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class VendaVeiculoRepositoryImplTest {

    @Autowired
    private JpaVendaVeiculoRepository jpaVendaVeiculoRepository;

    private VendaVeiculoRepositoryImpl vendaVeiculoRepositoryImpl;

    @BeforeEach
    void setUp() {
        this.vendaVeiculoRepositoryImpl = new VendaVeiculoRepositoryImpl(jpaVendaVeiculoRepository);
    }

    @Test
    void deveSalvarVendaComSucessoNoBancoDeDados() {

        UUID idVeiculo = UUID.randomUUID();
        BigDecimal preco = BigDecimal.valueOf(85000.00);
        String docComprador = "12345678910";
        String codPagmento = "AAAA-1234";


        VendaVeiculo venda = new VendaVeiculo(idVeiculo, preco, docComprador, codPagmento);

        VendaVeiculo vendaSalva = vendaVeiculoRepositoryImpl.salvar(venda);

        assertNotNull(vendaSalva);
        assertEquals(idVeiculo, vendaSalva.getIdVeiculo());
        assertEquals(docComprador, vendaSalva.getDocComprador());

        List<JpaVendaVeiculoEntity> todasVendas = jpaVendaVeiculoRepository.findAll();
        assertFalse(todasVendas.isEmpty());

        JpaVendaVeiculoEntity entidadeNoBanco = todasVendas.stream()
                .filter(v -> v.getIdVeiculo().equals(idVeiculo))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Venda não encontrada no banco para o veículo informado"));

        assertEquals(docComprador, entidadeNoBanco.getDocComprador());
        assertEquals(codPagmento, entidadeNoBanco.getCodigoPagamento());
    }
}