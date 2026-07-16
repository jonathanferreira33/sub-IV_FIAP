package com.cc.vendas.dominio.veiculo;

import com.cc.vendas.dominio.excecao.RegraNegocioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class StatusVeiculoTest {

    @Test
    void deveConverterParaAnaliseComSucesso() {
        StatusVeiculo status = StatusVeiculo.fromString("analise");
        assertEquals(StatusVeiculo.ANALISE, status);
    }

    @ParameterizedTest
    @ValueSource(strings = {"disponivel_para_venda", "disponivel", "disponível", "  DISPONIVEL  "})
    void deveConverterVariacoesDeDisponivelParaOEnumCorreto(String input) {
        StatusVeiculo status = StatusVeiculo.fromString(input);
        assertEquals(StatusVeiculo.DISPONIVEL_PARA_VENDA, status);
    }

    @Test
    void deveConverterParaVendidoComSucesso() {
        StatusVeiculo status = StatusVeiculo.fromString("VENDIDO");
        assertEquals(StatusVeiculo.VENDIDO, status);
    }

    @Test
    void deveLancarExcecaoQuandoStatusForNull() {
        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> StatusVeiculo.fromString(null)
        );
        assertEquals("Status do veículo não informado", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void deveLancarExcecaoQuandoStatusForVazioOuEmBranco(String input) {
        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> StatusVeiculo.fromString(input)
        );
        assertEquals("Status do veículo não informado", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoStatusForInvalido() {
        String statusInvalido = "invalido";
        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> StatusVeiculo.fromString(statusInvalido)
        );
        assertEquals("Status do veículo inválido: " + statusInvalido, exception.getMessage());
    }
}