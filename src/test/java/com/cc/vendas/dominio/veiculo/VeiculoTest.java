package com.cc.vendas.dominio.veiculo;

import com.cc.vendas.dominio.excecao.RegraNegocioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VeiculoTest {

    private final String marca = "Toyota";
    private final String modelo = "Corolla";
    private final String cor = "Branco";
    private final Integer ano = 2024;
    private final BigDecimal preco = BigDecimal.valueOf(120_000);
    private final String cpfComprador = "12345678910";

    @Test
    void deveCriarVeiculoComStatusAnalise() {
        Veiculo veiculo = Veiculo.criar(marca, modelo, cor, ano, preco);

        assertNotNull(veiculo);
        assertNotNull(veiculo.getId());
        assertEquals(marca.toUpperCase(Locale.ROOT), veiculo.getMarca().toString());
        assertEquals(modelo, veiculo.getModelo());
        assertEquals(cor.toUpperCase(Locale.ROOT), veiculo.getCor().toString());
        assertEquals(ano, veiculo.getAno());
        assertEquals(preco, veiculo.getPreco());
        assertEquals(StatusVeiculo.ANALISE, veiculo.getStatus());
        assertNull(veiculo.getDocComprador());
        assertNull(veiculo.getDataVenda());
    }

    @Test
    void deveReconstituirVeiculoComSucesso() {

        UUID idExistente = UUID.randomUUID();
        Instant dataVenda = Instant.now();

        Veiculo veiculo = Veiculo.reconstituir(
                idExistente, marca, modelo, cor, ano, preco,
                StatusVeiculo.VENDIDO, cpfComprador, dataVenda
        );

        assertNotNull(veiculo);
        assertEquals(idExistente, veiculo.getId());
        assertEquals(StatusVeiculo.VENDIDO, veiculo.getStatus());
        assertEquals(cpfComprador, veiculo.getDocComprador());
        assertEquals(dataVenda, veiculo.getDataVenda());
    }

    @Test
    void deveAtualizarDadosQuandoStatusPermitirEdicao() {

        Veiculo veiculo = Veiculo.criar(marca, modelo, cor, ano, preco);

        veiculo.atualizarDados("Honda", "Civic", "Preto", 2025, BigDecimal.valueOf(150_000));

        assertEquals("Civic", veiculo.getModelo());
        assertEquals(2025, veiculo.getAno());
    }

    @Test
    void deveAlterarStatusParaDisponivel() {

        Veiculo veiculo = Veiculo.criar(marca, modelo, cor, ano, preco);

        veiculo.alterarStatusParaDisponivel();

        assertEquals(StatusVeiculo.DISPONIVEL_PARA_VENDA, veiculo.getStatus());
    }

    @Test
    void deveRegistrarVendaComSucesso() {

        Veiculo veiculo = Veiculo.criar(marca, modelo, cor, ano, preco);
        veiculo.alterarStatusParaDisponivel();

        veiculo.registrarVenda(cpfComprador);

        assertEquals(StatusVeiculo.VENDIDO, veiculo.getStatus());
        assertEquals(cpfComprador, veiculo.getDocComprador());
        assertNotNull(veiculo.getDataVenda());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void deveLancarExcecaoQuandoModeloForInvalidoNaCriacao(String modeloInvalido) {
        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> Veiculo.criar(marca, modeloInvalido, cor, ano, preco)
        );
        assertEquals("Modelo obrigatório", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void deveLancarExcecaoQuandoAnoForInvalidoNaCriacao(Integer anoInvalido) {
        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> Veiculo.criar(marca, modelo, cor, anoInvalido, preco)
        );
        assertEquals("Ano inválido", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoPrecoForZeroOuNegativoNaCriacao() {
        RegraNegocioException exceptionZero = assertThrows(
                RegraNegocioException.class,
                () -> Veiculo.criar(marca, modelo, cor, ano, BigDecimal.ZERO)
        );
        assertEquals("Preço inválido", exceptionZero.getMessage());

        RegraNegocioException exceptionNegativo = assertThrows(
                RegraNegocioException.class,
                () -> Veiculo.criar(marca, modelo, cor, ano, BigDecimal.valueOf(-50))
        );
        assertEquals("Preço inválido", exceptionNegativo.getMessage());
    }

    @Test
    void deveLancarExcecaoAoTentarRegistrarVendaDeVeiculoNaoDisponivel() {
        Veiculo veiculo = Veiculo.criar(marca, modelo, cor, ano, preco);

        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> veiculo.registrarVenda(cpfComprador)
        );
        assertEquals("Veiculo indisponivel para venda", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoTentarEditarVeiculoVendido() {
        UUID idExistente = UUID.randomUUID();
        Veiculo veiculoVendido = Veiculo.reconstituir(
                idExistente,
                marca,
                modelo,
                cor,
                ano,
                preco,
                StatusVeiculo.VENDIDO,
                cpfComprador,
                Instant.now()
        );

        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> veiculoVendido.atualizarDados(marca, "Novo Modelo", cor, ano, preco)
        );
        assertEquals("Veiculo indisponivel para edição", exception.getMessage());
    }
}