package com.cc.vendas.infraestrutura.adaptadores.mapper;

import com.cc.vendas.dominio.pagamento.Pagamento;
import com.cc.vendas.infraestrutura.adaptadores.saida.entidades.JpaPagamentoEntity;
import com.cc.vendas.infraestrutura.adaptadores.saida.mapper.PagamentoMapper;
import com.cc.vendas.shared.StatusPagamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PagamentoMapperTest {

    private final UUID id = UUID.randomUUID();
    private final UUID vendaId = UUID.randomUUID();
    private final BigDecimal valor = BigDecimal.valueOf(1500);
    private final String codigoPagamento = "PIX123";
    private final StatusPagamento status = StatusPagamento.PENDENTE;
    private final Instant dataCriacao = Instant.now().minusSeconds(60);
    private final Instant dataAtualizacao = Instant.now();

    @Test
    void deveMapearDominioParaJpa() {

        Pagamento pagamento = Pagamento.reconstituir(
                id,
                vendaId,
                valor,
                codigoPagamento,
                status,
                dataCriacao,
                dataAtualizacao
        );

        JpaPagamentoEntity entity = PagamentoMapper.dominioParaJpa(pagamento);

        assertNotNull(entity);

        assertEquals(id, entity.getId());
        assertEquals(vendaId, entity.getVendaId());
        assertEquals(valor, entity.getValor());
        assertEquals(codigoPagamento, entity.getCodigoPagamento());
        assertEquals(status, entity.getStatus());
        assertEquals(dataCriacao, entity.getDataCriacao());
        assertEquals(dataAtualizacao, entity.getDataUltimaAtualizacao());
    }

    @Test
    void deveMapearJpaParaDominio() {

        JpaPagamentoEntity entity = new JpaPagamentoEntity(
                id,
                vendaId,
                valor,
                codigoPagamento,
                status,
                dataCriacao,
                dataAtualizacao
        );

        Pagamento pagamento = PagamentoMapper.jpaParaDominio(entity);

        assertNotNull(pagamento);

        assertEquals(id, pagamento.getId());
        assertEquals(vendaId, pagamento.getVendaId());
        assertEquals(valor, pagamento.getValor());
        assertEquals(codigoPagamento, pagamento.getCodigoExterno());
        assertEquals(status, pagamento.getStatus());
        assertEquals(dataCriacao, pagamento.getDataCriacao());
        assertEquals(dataAtualizacao, pagamento.getDataAtualizacao());
    }

    @Test
    void deveLancarExcecaoQuandoJpaForNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> PagamentoMapper.jpaParaDominio(null)
        );

        assertEquals(
                "Entidade JPA de pagamento não pode ser nula",
                exception.getMessage()
        );
    }
}
