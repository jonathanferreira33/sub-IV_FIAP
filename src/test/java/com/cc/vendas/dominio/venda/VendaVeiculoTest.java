package com.cc.vendas.dominio.venda;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VendaVeiculoTest {

    @Test
    void deveInstanciarVendaVeiculoComSucesso() {
        UUID idVeiculo = UUID.randomUUID();
        BigDecimal preco = BigDecimal.valueOf(95);
        String docComprador = "12345678901";

        VendaVeiculo venda = VendaVeiculo.criar(idVeiculo, preco, docComprador);

        assertNotNull(venda);
        assertNotNull(venda.getId());
        assertEquals(idVeiculo, venda.getIdVeiculo());
        assertEquals(preco, venda.getPreco());
        assertEquals(docComprador, venda.getDocComprador());
        assertNotNull(venda.getDataVenda());
    }
}
