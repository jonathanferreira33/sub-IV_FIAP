package com.cc.vendas.aplicacao.servicos;

import com.cc.vendas.aplicacao.casosdeuso.ConfirmarPagamentoUseCase;
import com.cc.vendas.aplicacao.dto.entrada.ConfirmacaoPagamentoInput;
import com.cc.vendas.aplicacao.dto.entrada.RegistrarPagamentoInput;
import com.cc.vendas.aplicacao.dto.mapper.PagamentoAppMapper;
import com.cc.vendas.aplicacao.dto.saida.PagamentoOutput;
import com.cc.vendas.dominio.pagamento.Pagamento;
import com.cc.vendas.dominio.pagamento.PagamentoRepository;

import java.util.Optional;


public class PagamentoServiceImpl implements ConfirmarPagamentoUseCase {

    private final PagamentoRepository repository;

    public PagamentoServiceImpl(PagamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public PagamentoOutput cadastrarPagamento(RegistrarPagamentoInput input) {
        Pagamento pagamento = Pagamento.criar(
                input.vendaId(),
                input.valor(),
                input.codigoPagamento()
        );

        repository.cadastrarPagamento(pagamento);

        // TODO CHAMAR MENSAGERIA

        return PagamentoAppMapper.pagamentoParaOutput(pagamento);
    }

    @Override
    public void confirmar(ConfirmacaoPagamentoInput input) {

        Optional<Pagamento> pagamento = repository.buscarPagamentoPorCodPagamento(input.codigoPagamento());

        if (pagamento.isPresent()) {
            pagamento.get().atualizarStatus(input.statusPagamento());
            repository.salvar(pagamento.get());
        }
    }
}
