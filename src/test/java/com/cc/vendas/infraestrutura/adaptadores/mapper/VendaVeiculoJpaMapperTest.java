package com.cc.vendas.infraestrutura.adaptadores.web.mapper;

import com.cc.vendas.dominio.venda.VendaVeiculo;
import com.cc.vendas.infraestrutura.adaptadores.saida.entidades.JpaVendaVeiculoEntity;
import com.cc.vendas.infraestrutura.adaptadores.saida.mapper.VendaVeiculoJpaMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class VendaVeiculoJpaMapperTest {

    private final UUID idVeiculo = UUID.randomUUID();
    private final BigDecimal preco = BigDecimal.valueOf(85000);
    private final String docComprador = "12345678910";

    @Test
    void deveMapearDominioParaJpa() {
        VendaVeiculo venda = VendaVeiculo.criar(
                idVeiculo,
                preco,
                docComprador
        );

        JpaVendaVeiculoEntity entity = VendaVeiculoJpaMapper.dominioParaJpa(venda);

        assertNotNull(entity);

        assertEquals(idVeiculo, entity.getIdVeiculo());
        assertEquals(preco, entity.getPreco());
        assertEquals(docComprador, entity.getDocComprador());

        assertNotNull(entity.getDataCompra());
    }

    @Test
    void deveMapearJpaParaDominio() {
        JpaVendaVeiculoEntity entity = new JpaVendaVeiculoEntity(
                idVeiculo,
                preco,
                docComprador,
                LocalDateTime.now()
        );

        VendaVeiculo venda = VendaVeiculoJpaMapper.jpaParaDominio(entity);

        assertNotNull(venda);

        assertEquals(idVeiculo, venda.getIdVeiculo());
        assertEquals(preco, venda.getPreco());
        assertEquals(docComprador, venda.getDocComprador());

        assertNotNull(venda.getDataVenda());
    }

    @Test
    void deveLancarExcecaoQuandoJpaForNull() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> VendaVeiculoJpaMapper.jpaParaDominio(null)
        );

        assertEquals(
                "Entidade JPA de Venda Veiculo não pode ser nula",
                exception.getMessage()
        );
    }
}
