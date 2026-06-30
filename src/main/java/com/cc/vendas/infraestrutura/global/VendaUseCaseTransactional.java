package com.cc.vendas.infraestrutura.global;

import com.cc.vendas.aplicacao.casosdeuso.VendaUseCase;
import com.cc.vendas.aplicacao.dto.saida.VeiculoResumoOutput;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
public class VendaUseCaseTransactional implements VendaUseCase {

    private final VendaUseCase delegate;

    public VendaUseCaseTransactional(VendaUseCase delegate) {
        this.delegate = delegate;
    }

    @Override
    public VeiculoResumoOutput registrarVenda(UUID id, String docComprador) {
        return delegate.registrarVenda(id, docComprador);
    }
}