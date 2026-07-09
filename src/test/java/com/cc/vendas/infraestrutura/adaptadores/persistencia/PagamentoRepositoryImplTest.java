package com.cc.vendas.infraestrutura.adaptadores.persistencia;

import com.cc.vendas.dominio.pagamento.Pagamento;
import com.cc.vendas.infraestrutura.adaptadores.saida.entidades.JpaPagamentoEntity;
import com.cc.vendas.infraestrutura.adaptadores.saida.persistencia.repositorios.JpaPagamentoRepository;
import com.cc.vendas.infraestrutura.adaptadores.saida.persistencia.repositorios.PagamentoRepositoryImpl;
import com.cc.vendas.shared.StatusPagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class PagamentoRepositoryImplTest {

    @Autowired
    private JpaPagamentoRepository jpaPagamentoRepository;

    private PagamentoRepositoryImpl pagamentoRepositoryImpl;

    private final UUID vendaId = UUID.randomUUID();
    private final BigDecimal valor = BigDecimal.valueOf(150.00);
    private final String codigoPagamento = "COD-TESTE-123";

    @BeforeEach
    void setUp() {
        this.pagamentoRepositoryImpl = new PagamentoRepositoryImpl(jpaPagamentoRepository);
    }

    @Test
    void deveCadastrarPagamentoComSucessoUsandoMetodoCriar() {

        Pagamento pagamento = Pagamento.criar(vendaId, valor, codigoPagamento);

        Pagamento cadastrado = pagamentoRepositoryImpl.cadastrarPagamento(pagamento);

        assertNotNull(cadastrado);
        assertEquals(pagamento.getId(), cadastrado.getId());
        assertEquals(codigoPagamento, cadastrado.getCodigoExterno());
        assertEquals(StatusPagamento.PENDENTE, cadastrado.getStatus());

        Optional<JpaPagamentoEntity> noBanco = jpaPagamentoRepository.findById(pagamento.getId());
        assertTrue(noBanco.isPresent());
        assertEquals(codigoPagamento, noBanco.get().getCodigoPagamento());
    }

    @Test
    void deveBuscarPagamentoPorIdComSucesso() {

        Pagamento pagamento = Pagamento.criar(vendaId, valor, codigoPagamento);
        pagamentoRepositoryImpl.cadastrarPagamento(pagamento);

        Optional<Pagamento> encontrado = pagamentoRepositoryImpl.buscarPagamentoPorId(pagamento.getId());

        assertTrue(encontrado.isPresent());
        assertEquals(pagamento.getId(), encontrado.get().getId());
    }

    @Test
    void deveRetornarOptionalVazioAoBuscarPorIdInexistente() {
        Optional<Pagamento> encontrado = pagamentoRepositoryImpl.buscarPagamentoPorId(UUID.randomUUID());
        assertTrue(encontrado.isEmpty());
    }

    @Test
    void deveBuscarPagamentoPorCodigoComSucessoUsandoReconstituir() {

        UUID idExistente = UUID.randomUUID();
        Pagamento pagamento = Pagamento.reconstituir(
                idExistente, vendaId, valor, codigoPagamento,
                StatusPagamento.CONFIRMADO, Instant.now(), Instant.now()
        );
        pagamentoRepositoryImpl.cadastrarPagamento(pagamento);

        Optional<Pagamento> encontrado = pagamentoRepositoryImpl.buscarPagamentoPorCodPagamento(codigoPagamento);

        assertTrue(encontrado.isPresent());
        assertEquals(codigoPagamento, encontrado.get().getCodigoExterno());
        assertEquals(StatusPagamento.CONFIRMADO, encontrado.get().getStatus());
    }

    @Test
    void deveRetornarOptionalVazioAoBuscarPorCodigoInexistente() {

        Optional<Pagamento> encontrado = pagamentoRepositoryImpl.buscarPagamentoPorCodPagamento("CODIGO-INEXISTENTE");
        assertTrue(encontrado.isEmpty());
    }

    @Test
    void deveSalvarOuAtualizarPagamentoExistenteComMetodoSalvar() {
        Pagamento pagamento = Pagamento.criar(vendaId, valor, codigoPagamento);

        pagamentoRepositoryImpl.salvar(pagamento);

        Optional<JpaPagamentoEntity> noBanco = jpaPagamentoRepository.findById(pagamento.getId());
        assertTrue(noBanco.isPresent());
        assertEquals(vendaId, noBanco.get().getVendaId());
    }
}