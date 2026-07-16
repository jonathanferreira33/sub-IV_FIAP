package com.cc.vendas.aplicacao.mapper;

import com.cc.vendas.aplicacao.dto.mapper.VeiculoAppMapper;
import com.cc.vendas.aplicacao.dto.saida.VeiculoResumoOutput;
import com.cc.vendas.dominio.veiculo.Veiculo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class VeiculoAppMapperTest {

    @Test
    void deveMapearVeiculoDisponivel() {
        Veiculo veiculo = Veiculo.criar(
                "Toyota",
                "Corolla",
                "Branco",
                2023,
                BigDecimal.valueOf(125000)
        );

        veiculo.alterarStatusParaDisponivel();

        VeiculoResumoOutput output =
                VeiculoAppMapper.veiculoParaResumoOutput(veiculo);

        assertNotNull(output);
        assertEquals(veiculo.getId(), output.id());
        assertEquals(veiculo.getMarca().toString(), output.marca());
        assertEquals(veiculo.getModelo(), output.modelo());
        assertEquals(veiculo.getCor().toString(), output.cor());
        assertEquals(veiculo.getAno(), output.ano());
        assertEquals(veiculo.getPreco(), output.preco());
        assertEquals(veiculo.getStatus(), output.statusVeiculo());

        assertNull(output.dataVenda());
    }

}
