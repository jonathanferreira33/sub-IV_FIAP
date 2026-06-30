package com.cc.vendas.aplicacao.dto;

import com.cc.vendas.aplicacao.dto.entrada.ConfirmacaoPagamentoInput;
import com.cc.vendas.shared.StatusPagamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConfirmacaoPagamentoInputTest {

    private final UUID vendaId = UUID.randomUUID();
    private final String codPagamento = "PIX123";

    @Test
    void deveCriarConfirmacaoPagamentoInput() {
        ConfirmacaoPagamentoInput input =
                new ConfirmacaoPagamentoInput(
                        vendaId,
                        BigDecimal.TEN,
                        codPagamento,
                        StatusPagamento.CONFIRMADO
                );

        assertNotNull(input);
        assertEquals(codPagamento, input.codigoPagamento());
    }

    @Test
    void deveLancarExcecaoQuandoCodigoPagamentoForNull() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new ConfirmacaoPagamentoInput(
                                vendaId,
                                BigDecimal.TEN,
                                null,
                                StatusPagamento.CONFIRMADO
                        )
                );

        assertEquals(
                "Código do pagamento é obrigatório",
                exception.getMessage()
        );
    }

    @Test
    void deveLancarExcecaoQuandoCodigoPagamentoForVazio() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new ConfirmacaoPagamentoInput(
                                vendaId,
                                BigDecimal.TEN,
                                "",
                                StatusPagamento.CONFIRMADO
                        )
                );

        assertEquals(
                "Código do pagamento é obrigatório",
                exception.getMessage()
        );
    }

    @Test
    void deveLancarExcecaoQuandoCodigoPagamentoForEmBranco() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new ConfirmacaoPagamentoInput(
                                vendaId,
                                BigDecimal.TEN,
                                "   ",
                                StatusPagamento.CONFIRMADO
                        )
                );

        assertEquals(
                "Código do pagamento é obrigatório",
                exception.getMessage()
        );
    }
}
