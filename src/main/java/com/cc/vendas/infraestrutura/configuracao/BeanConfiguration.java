package com.cc.vendas.infraestrutura.configuracao;

import com.cc.vendas.aplicacao.casosdeuso.VeiculoUseCase;
import com.cc.vendas.aplicacao.gateway.PagamentoGateway;
import com.cc.vendas.aplicacao.servicos.VeiculoServiceImpl;
import com.cc.vendas.dominio.veiculo.VeiculoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public VeiculoUseCase veiculoUseCase(VeiculoRepository repository) {
        return new VeiculoServiceImpl(repository);
    }

}
