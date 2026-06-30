package com.cc.vendas.aplicacao.dto;

import com.cc.vendas.aplicacao.dto.entrada.RegistrarVeiculoInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RegistrarVeiculoInputTest {

    private final String marca = "Toyota";
    private final String modelo = "Corolla";
    private final String cor = "Branco";
    private final Integer ano = 2024;
    private final BigDecimal preco = BigDecimal.valueOf(100_000);

    @Test
    void deveCriarRegistrarVeiculoInput() {
        RegistrarVeiculoInput input = new RegistrarVeiculoInput(
                marca,
                modelo,
                cor,
                ano,
                preco
        );

        assertNotNull(input);
        assertEquals(marca, input.marca());
        assertEquals(modelo, input.modelo());
        assertEquals(cor, input.cor());
        assertEquals(ano, input.ano());
        assertEquals(preco, input.preco());
    }


    @Test
    void deveLancarExcecaoQuandoMarcaForNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new RegistrarVeiculoInput(
                        null,
                        modelo,
                        cor,
                        ano,
                        preco
                )
        );

        assertEquals("Marca é obrigatória", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoMarcaForVazia() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new RegistrarVeiculoInput(
                        "",
                        modelo,
                        cor,
                        ano,
                        preco
                )
        );

        assertEquals("Marca é obrigatória", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoMarcaForEmBranco() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new RegistrarVeiculoInput(
                        "   ",
                        modelo,
                        cor,
                        ano,
                        preco
                )
        );

        assertEquals("Marca é obrigatória", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoModeloForNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new RegistrarVeiculoInput(
                        marca,
                        null,
                        cor,
                        ano,
                        preco
                )
        );

        assertEquals("Modelo é obrigatório", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoModeloForVazio() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new RegistrarVeiculoInput(
                        marca,
                        "",
                        cor,
                        ano,
                        preco
                )
        );

        assertEquals("Modelo é obrigatório", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoPrecoForNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new RegistrarVeiculoInput(
                        marca,
                        modelo,
                        cor,
                        ano,
                        null
                )
        );
        assertEquals("Preço inválido", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoPrecoForZero() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new RegistrarVeiculoInput(
                        marca,
                        modelo,
                        cor,
                        ano,
                        BigDecimal.ZERO
                )
        );

        assertEquals("Preço inválido", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoPrecoForNegativo() {

        BigDecimal preco = BigDecimal.valueOf(-1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new RegistrarVeiculoInput(
                        marca,
                        modelo,
                        cor,
                        ano,
                        preco
                )
        );

        assertEquals("Preço inválido", exception.getMessage());
    }

}
