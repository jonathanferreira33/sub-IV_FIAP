package com.cc.vendas.infraestrutura.adaptadores.persistencia;

import com.cc.vendas.dominio.veiculo.Veiculo;
import com.cc.vendas.infraestrutura.adaptadores.saida.entidades.JpaVeiculoEntity;
import com.cc.vendas.infraestrutura.adaptadores.saida.persistencia.repositorios.JpaVeiculoRepository;
import com.cc.vendas.infraestrutura.adaptadores.saida.persistencia.repositorios.VeiculoRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VeiculoRepositoryImplTest {

    @Autowired
    private JpaVeiculoRepository jpaVeiculoRepository;

    private VeiculoRepositoryImpl veiculoRepositoryImpl;

    @BeforeEach
    void setUp() {
        this.veiculoRepositoryImpl = new VeiculoRepositoryImpl(
                jpaVeiculoRepository
        );

        JpaVeiculoEntity veiculo = new JpaVeiculoEntity(
                UUID.randomUUID(),
                "Toyota",
                "Corolla",
                "Branco",
                2024,
                BigDecimal.valueOf(100000),
                "DISPONIVEL",
                "12345678910",
                Instant.now()
        );
        jpaVeiculoRepository.save(veiculo);
    }

    @Test
    void deveSalvarEVersionarVeiculoComSucesso() {
        Veiculo veiculo = Veiculo.criar("Toyota", "Corolla", "Branco", 2024, BigDecimal.valueOf(100000));

        Veiculo veiculoSalvo = veiculoRepositoryImpl.salvar(veiculo);

        assertNotNull(veiculoSalvo);
        assertEquals(veiculo.getId(), veiculoSalvo.getId());
        assertEquals("Toyota".toUpperCase(Locale.ROOT), veiculoSalvo.getMarca().toString());

        Optional<JpaVeiculoEntity> noBanco = jpaVeiculoRepository.findById(veiculo.getId());
        assertTrue(noBanco.isPresent());
        assertEquals("Corolla", noBanco.get().getModelo());
    }

    @Test
    void deveBuscarVeiculoPorIdComSucesso() {
        // 1. Cria com ID fixo ou garante que pegamos o ID depois de salvar
        Veiculo veiculo = Veiculo.criar("Honda", "Civic", "Preto", 2023, BigDecimal.valueOf(120000));

        Veiculo salvo = veiculoRepositoryImpl.salvar(veiculo);

        Optional<Veiculo> veiculoEncontrado = veiculoRepositoryImpl.buscarPorId(salvo.getId());

        assertTrue(veiculoEncontrado.isPresent(), "O veículo deveria ter sido encontrado no banco");
        assertEquals("Civic", veiculoEncontrado.get().getModelo());
    }

    @Test
    void deveRetornarOptionalVazioAoBuscarPorIdInexistente() {
        Optional<Veiculo> veiculoEncontrado = veiculoRepositoryImpl.buscarPorId(UUID.randomUUID());
        assertTrue(veiculoEncontrado.isEmpty());
    }

    @Test
    void deveBuscarTodosVeiculosPorStatusOrdenadoPorPrecoAscendente() {
        Veiculo carroCaro = Veiculo.criar(
                "BMW",
                "320i",
                "Azul",
                2024,
                BigDecimal.valueOf(300000));
        Veiculo carroBarato = Veiculo.criar(
                "Fiat",
                "Uno",
                "Branco",
                2012,
                BigDecimal.valueOf(15000));
        Veiculo carroMedio = Veiculo.criar(
                "Ford",
                "Ka",
                "Prata",
                2020,
                BigDecimal.valueOf(50000));

        veiculoRepositoryImpl.salvar(carroCaro);
        veiculoRepositoryImpl.salvar(carroBarato);
        veiculoRepositoryImpl.salvar(carroMedio);

        List<Veiculo> resultado = veiculoRepositoryImpl.buscarTodosVeiculosPorStatusOrdenadoPorPreco("ANALISE");

        assertNotNull(resultado);
        assertEquals(3, resultado.size());

        assertEquals("Uno", resultado.get(0).getModelo());
        assertEquals("Ka", resultado.get(1).getModelo());
        assertEquals("320i", resultado.get(2).getModelo());
    }
}