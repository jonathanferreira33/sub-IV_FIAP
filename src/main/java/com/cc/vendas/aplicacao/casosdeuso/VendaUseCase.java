package com.cc.vendas.aplicacao.casosdeuso;


import com.cc.vendas.aplicacao.dto.saida.VeiculoResumoOutput;

import java.util.UUID;

public interface VendaUseCase {
    VeiculoResumoOutput registrarVenda(UUID id, String docComprador);
}
