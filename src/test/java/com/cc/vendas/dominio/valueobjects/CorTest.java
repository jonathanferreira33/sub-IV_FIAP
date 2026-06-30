package com.cc.vendas.dominio.valueobjects;

import com.cc.vendas.dominio.excecao.RegraNegocioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CorTest {

    @Test
    @DisplayName("Deve criar cor válida e normalizar para uppercase")
    void deveCriarCorValida() {
        Cor cor = new Cor("azul");

        assertThat(cor.valor()).isEqualTo("AZUL");
    }

    @Test
    @DisplayName("Deve remover espaços e normalizar")
    void deveRemoverEspacos() {
        Cor cor = new Cor(" azul ");

        assertThat(cor.valor()).isEqualTo("AZUL");
    }

    @Test
    @DisplayName("Deve lançar exceção quando cor for nula")
    void deveLancarExcecaoQuandoCorNula() {
        assertThatThrownBy(() -> new Cor(null))
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage("A cor é obrigatória");
    }

    @Test
    @DisplayName("Deve lançar exceção quando cor for vazia")
    void deveLancarExcecaoQuandoCorVazia() {
        assertThatThrownBy(() -> new Cor(" "))
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage("A cor é obrigatória");
    }

    @Test
    @DisplayName("Deve lançar exceção quando cor não for permitida")
    void deveLancarExcecaoQuandoCorInvalida() {
        assertThatThrownBy(() -> new Cor("rosa neon"))
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage("A cor 'rosa neon' não é permitida pelo sistema");
    }
}
