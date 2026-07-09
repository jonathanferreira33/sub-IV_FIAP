package com.cc.vendas.dominio.utils;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GeneradorCodigoPagamentoTest {

    @RepeatedTest(10)
    void deveGerarCodigoNoFormatoCorreto() {
        String codigo = GeneradorCodigoPagamento.gerarCodigo();

        assertEquals(9, codigo.length());
        assertEquals('-', codigo.charAt(4));

        String parteLetras = codigo.substring(0, 4);
        assertTrue(parteLetras.matches("[A-Z]{4}"));

        String parteNumeros = codigo.substring(5);
        assertTrue(parteNumeros.matches("\\d{4}"));
    }

    @Test
    void deveGerarCodigosDiferentes() {
        String codigo1 = GeneradorCodigoPagamento.gerarCodigo();
        String codigo2 = GeneradorCodigoPagamento.gerarCodigo();

        assertNotEquals(codigo1, codigo2);
    }
}
