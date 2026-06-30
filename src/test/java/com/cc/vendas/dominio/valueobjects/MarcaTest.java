package com.cc.vendas.dominio.valueobjects;

import com.cc.vendas.dominio.excecao.RegraNegocioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class MarcaTest {

    @Test
    @DisplayName("Deve criar marca válida e normalizar para uppercase")
    void deveCriarMarcaValida() {
        Marca marca = new Marca("toyota");

        assertThat(marca.valor()).isEqualTo("TOYOTA");
    }

    @Test
    @DisplayName("Deve remover espaços e normalizar")
    void deveRemoverEspacos() {
        Marca marca = new Marca(" ford ");

        assertThat(marca.valor()).isEqualTo("FORD");
    }

    @Test
    @DisplayName("Deve reconhecer inicial maiscula e normalizar")
    void deveReconhecerInialMaiscula() {
        Marca marca = new Marca("Ford");

        assertThat(marca.valor()).isEqualTo("FORD");
    }

    @Test
    @DisplayName("Deve lançar exceção quando marca for nula")
    void deveLancarExcecaoQuandoMarcaNula() {
        assertThatThrownBy(() -> new Marca(null))
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage("O marca é obrigatório");
    }

    @Test
    @DisplayName("Deve lançar exceção quando marca for vazia")
    void deveLancarExcecaoQuandoMarcaVazia() {
        assertThatThrownBy(() -> new Marca(" "))
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage("O marca é obrigatório");
    }

    @Test
    @DisplayName("Deve lançar exceção quando marca não for permitida")
    void deveLancarExcecaoQuandoMarcaInvalida() {
        assertThatThrownBy(() -> new Marca("Tesla"))
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage("A marca 'Tesla' não é permitida pelo sistema");
    }
}
