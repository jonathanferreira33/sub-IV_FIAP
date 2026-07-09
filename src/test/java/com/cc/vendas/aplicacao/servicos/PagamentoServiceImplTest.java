package com.cc.vendas.aplicacao.servicos;

import com.cc.vendas.aplicacao.casosdeuso.VendaEventPublisher;
import com.cc.vendas.aplicacao.dto.entrada.ConfirmacaoPagamentoInput;
import com.cc.vendas.aplicacao.dto.entrada.RegistrarPagamentoInput;
import com.cc.vendas.dominio.pagamento.Pagamento;
import com.cc.vendas.dominio.pagamento.PagamentoRepository;
import com.cc.vendas.infraestrutura.adaptadores.saida.mensageria.PagamentoCriadoEvent;
import com.cc.vendas.shared.StatusPagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceImplTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        service = new PagamentoServiceImpl(
                repository,
                publisher
        );
    }

    @Mock
    private VendaEventPublisher publisher;

    @Mock
    private PagamentoRepository repository;

    @InjectMocks
    private PagamentoServiceImpl service;

    private final UUID vendaId = UUID.randomUUID();
    private final String codPagamento = "PIX123";

    @Test
    void deveCadastrarPagamento() {

        RegistrarPagamentoInput input =
                new RegistrarPagamentoInput(
                        vendaId,
                        BigDecimal.valueOf(150.0),
                        codPagamento
                );

        service.cadastrarPagamento(input);

        ArgumentCaptor<Pagamento> captor =
                ArgumentCaptor.forClass(Pagamento.class);

        verify(repository).cadastrarPagamento(captor.capture());
        verify(publisher).publicarVendaRegistrada(any(PagamentoCriadoEvent.class));
        Pagamento pagamento = captor.getValue();

        assertEquals(vendaId, pagamento.getVendaId());
        assertEquals(BigDecimal.valueOf(150.0), pagamento.getValor());
        assertEquals(codPagamento, pagamento.getCodigoExterno());

        assertEquals(StatusPagamento.PENDENTE, pagamento.getStatus());

        assertNotNull(pagamento.getId());
        assertNotNull(pagamento.getDataCriacao());
        assertNotNull(pagamento.getDataAtualizacao());
    }

    @Test
    void naoDeveSalvarQuandoPagamentoNaoExiste() {

        when(repository.buscarPagamentoPorCodPagamento(codPagamento))
                .thenReturn(Optional.empty());

        ConfirmacaoPagamentoInput input =
                new ConfirmacaoPagamentoInput(
                        vendaId,
                        BigDecimal.valueOf(100),
                        codPagamento,
                        StatusPagamento.CONFIRMADO
                );

        service.confirmar(input);

        verify(repository).buscarPagamentoPorCodPagamento(codPagamento);
        verify(repository, never()).salvar(any());
    }

    @Test
    void deveConfirmarPagamento() {

        Pagamento pagamento =
                Pagamento.criar(
                        UUID.randomUUID(),
                        BigDecimal.TEN,
                        codPagamento
                );

        when(repository.buscarPagamentoPorCodPagamento(codPagamento))
                .thenReturn(Optional.of(pagamento));

        ConfirmacaoPagamentoInput input =
                new ConfirmacaoPagamentoInput(
                        vendaId,
                        BigDecimal.valueOf(100),
                        codPagamento,
                        StatusPagamento.CONFIRMADO
                );

        service.confirmar(input);

        verify(repository).salvar(pagamento);

        assertEquals(
                StatusPagamento.CONFIRMADO,
                pagamento.getStatus()
        );
    }

}
