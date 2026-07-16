package com.cc.vendas.infraestrutura.adaptadores.persistencia;

import com.cc.vendas.dominio.veiculo.StatusVeiculo;
import com.cc.vendas.dominio.veiculo.Veiculo;
import com.cc.vendas.infraestrutura.adaptadores.saida.entidades.JpaVeiculoEntity;
import com.cc.vendas.infraestrutura.adaptadores.saida.persistencia.repositorios.JpaVeiculoRepository;
import com.cc.vendas.infraestrutura.adaptadores.saida.persistencia.repositorios.VeiculoRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

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

    @Test
    void deveAtualizarVeiculoQuandoJaExisteEStatusDiferenteDeVendido() {
        Veiculo veiculoOriginal = Veiculo.criar("Ford", "Fiesta", "Azul", 2019, BigDecimal.valueOf(40000));
        Veiculo salvoInicialmente = veiculoRepositoryImpl.salvar(veiculoOriginal);

        ReflectionTestUtils.setField(salvoInicialmente, "preco", BigDecimal.valueOf(45000));

        Veiculo atualizado = veiculoRepositoryImpl.salvar(salvoInicialmente);

        Optional<JpaVeiculoEntity> noBanco = jpaVeiculoRepository.findById(atualizado.getId());
        assertTrue(noBanco.isPresent());

        assertEquals(0, new BigDecimal("45000").compareTo(noBanco.get().getPreco()));
        assertNull(noBanco.get().getDataVenda());
    }

    @Test
    void deveAtualizarVeiculoESetarDataVendaQuandoStatusForVendido() {
        Veiculo veiculoOriginal = Veiculo.criar("Chevrolet", "Onix", "Preto", 2021, BigDecimal.valueOf(60000));
        Veiculo salvoInicialmente = veiculoRepositoryImpl.salvar(veiculoOriginal);

        Instant dataVenda = Instant.now();
        ReflectionTestUtils.setField(salvoInicialmente, "status", StatusVeiculo.VENDIDO);
        ReflectionTestUtils.setField(salvoInicialmente, "dataVenda", dataVenda);

        veiculoRepositoryImpl.salvar(salvoInicialmente);

        Optional<JpaVeiculoEntity> noBanco = jpaVeiculoRepository.findById(salvoInicialmente.getId());
        assertTrue(noBanco.isPresent());

        assertEquals("VENDIDO", noBanco.get().getStatusVeiculo());
        assertNotNull(noBanco.get().getDataVenda());
        assertEquals(dataVenda, noBanco.get().getDataVenda());
    }
}
