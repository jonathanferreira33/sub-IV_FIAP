package com.cc.vendas.infraestrutura.adaptadores.web.mapper;

import com.cc.vendas.dominio.veiculo.StatusVeiculo;
import com.cc.vendas.dominio.veiculo.Veiculo;
import com.cc.vendas.infraestrutura.adaptadores.saida.entidades.JpaVeiculoEntity;
import com.cc.vendas.infraestrutura.adaptadores.saida.mapper.VeiculoJpaMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class VeiculoJpaMapperTest {

    private final UUID id = UUID.randomUUID();
    private final String marca = "Toyota";
    private final String modelo = "Corolla";
    private final String cor = "Branco";
    private final Integer ano = 2024;
    private final BigDecimal preco = BigDecimal.valueOf(120000);
    private final StatusVeiculo status = StatusVeiculo.DISPONIVEL_PARA_VENDA;
    private final String docComprador = "12345678910";
    private final Instant dataVenda = Instant.now();

    @Test
    void deveMapearDominioParaJpa() {

        Veiculo veiculo = Veiculo.reconstituir(
                id,
                marca,
                modelo,
                cor,
                ano,
                preco,
                status,
                docComprador,
                dataVenda
        );

        JpaVeiculoEntity entity = VeiculoJpaMapper.dominioParaJpa(veiculo);

        assertNotNull(entity);

        assertEquals(id, entity.getId());
        assertEquals(marca.toUpperCase(Locale.ROOT), entity.getMarca());
        assertEquals(modelo, entity.getModelo());
        assertEquals(cor.toUpperCase(Locale.ROOT), entity.getCor());
        assertEquals(ano, entity.getAno());
        assertEquals(preco, entity.getPreco());
        assertEquals(status.name(), entity.getStatusVeiculo());
        assertEquals(docComprador, entity.getDocComprador());
        assertEquals(dataVenda, entity.getDataVenda());
    }

    @Test
    void deveMapearJpaParaDominio() {

        JpaVeiculoEntity entity = new JpaVeiculoEntity(
                id,
                marca,
                modelo,
                cor,
                ano,
                preco,
                status.name(),
                docComprador,
                dataVenda
        );

        Veiculo veiculo = VeiculoJpaMapper.jpaParaDominio(entity);

        assertNotNull(veiculo);

        assertEquals(id, veiculo.getId());
        assertEquals(marca.toUpperCase(Locale.ROOT), veiculo.getMarca().valor());
        assertEquals(modelo, veiculo.getModelo());
        assertEquals(cor.toUpperCase(Locale.ROOT), veiculo.getCor().valor());
        assertEquals(ano, veiculo.getAno());
        assertEquals(preco, veiculo.getPreco());
        assertEquals(status, veiculo.getStatus());
        assertEquals(docComprador, veiculo.getDocComprador());
        assertEquals(dataVenda, veiculo.getDataVenda());
    }

    @Test
    void deveLancarExcecaoQuandoJpaForNull() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> VeiculoJpaMapper.jpaParaDominio(null)
        );

        assertEquals(
                "Entidade JPA de Veículo não pode ser nula",
                exception.getMessage()
        );
    }

}
