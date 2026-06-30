package com.cc.vendas.dominio.pagamento;

import com.cc.vendas.dominio.excecao.RegraNegocioException;
import com.cc.vendas.shared.StatusPagamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PagamentoTest {

    private final UUID vendaId = UUID.randomUUID();
    private final BigDecimal valor = BigDecimal.valueOf(150.50);
    private final String codigoExterno = "PAY-123456";

    @Test
    void deveCriarPagamentoComStatusPendente() {
        Pagamento pagamento = Pagamento.criar(vendaId, valor, codigoExterno);

        assertNotNull(pagamento);
        assertNotNull(pagamento.getId());
        assertEquals(vendaId, pagamento.getVendaId());
        assertEquals(valor, pagamento.getValor());
        assertEquals(codigoExterno, pagamento.getCodigoExterno());
        assertEquals(StatusPagamento.PENDENTE, pagamento.getStatus());
        assertNotNull(pagamento.getDataCriacao());
        assertNotNull(pagamento.getDataAtualizacao());
        assertFalse(pagamento.estaPago());
    }

    @Test
    void deveReconstituirPagamento() {
        UUID idExistente = UUID.randomUUID();
        Instant criadoEm = Instant.now().minusSeconds(3600);
        Instant atualizadoEm = Instant.now();
        StatusPagamento statusConfirmado = StatusPagamento.CONFIRMADO;

        Pagamento pagamento = Pagamento.reconstituir(
                idExistente,
                vendaId,
                valor,
                codigoExterno,
                statusConfirmado,
                criadoEm,
                atualizadoEm
        );

        assertNotNull(pagamento);
        assertEquals(idExistente, pagamento.getId());
        assertEquals(vendaId, pagamento.getVendaId());
        assertEquals(valor, pagamento.getValor());
        assertEquals(codigoExterno, pagamento.getCodigoExterno());
        assertEquals(statusConfirmado, pagamento.getStatus());
        assertEquals(criadoEm, pagamento.getDataCriacao());
        assertEquals(atualizadoEm, pagamento.getDataAtualizacao());
        assertTrue(pagamento.estaPago());
    }

    @Test
    void deveAtualizarStatusComSucesso() {
        Pagamento pagamento = Pagamento.criar(vendaId, valor, codigoExterno);
        Instant atualizacaoAnterior = pagamento.getDataAtualizacao();

        pagamento.atualizarStatus(StatusPagamento.CONFIRMADO);

        assertEquals(StatusPagamento.CONFIRMADO, pagamento.getStatus());
        assertTrue(pagamento.getDataAtualizacao().isAfter(atualizacaoAnterior) || pagamento.getDataAtualizacao().equals(atualizacaoAnterior));
    }

    @Test
    void deveConfirmarPagamentoComSucesso() {

        Pagamento pagamento = Pagamento.criar(vendaId, valor, codigoExterno);

        pagamento.confirmar();

        assertEquals(StatusPagamento.CONFIRMADO, pagamento.getStatus());
        assertTrue(pagamento.estaPago());
    }

    @Test
    void deveCancelarPagamentoComSucesso() {

        Pagamento pagamento = Pagamento.criar(vendaId, valor, codigoExterno);

        pagamento.cancelar();

        assertEquals(StatusPagamento.CANCELADO, pagamento.getStatus());
        assertFalse(pagamento.estaPago());
    }

    @Test
    void deveLancarExcecaoQuandoAtualizarStatusForInvalido() {

        Pagamento pagamento = Pagamento.reconstituir(
                UUID.randomUUID(), vendaId, valor, codigoExterno,
                StatusPagamento.CANCELADO, Instant.now(), Instant.now()
        );

        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> pagamento.atualizarStatus(StatusPagamento.CONFIRMADO)
        );

        assertEquals("Transição inválida", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoConfirmarTransicaoInvalida() {

        Pagamento pagamento = Pagamento.reconstituir(
                UUID.randomUUID(), vendaId, valor, codigoExterno,
                StatusPagamento.CANCELADO, Instant.now(), Instant.now()
        );

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                pagamento::confirmar
        );

        assertEquals("Transição inválida de status", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCancelarTransicaoInvalida() {

        Pagamento pagamento = Pagamento.reconstituir(
                UUID.randomUUID(), vendaId, valor, codigoExterno,
                StatusPagamento.CONFIRMADO, Instant.now(), Instant.now()
        );

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                pagamento::cancelar
        );

        assertEquals("Transição inválida de status", exception.getMessage());
    }

}