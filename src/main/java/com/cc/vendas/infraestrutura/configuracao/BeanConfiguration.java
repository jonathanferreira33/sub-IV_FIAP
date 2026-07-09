package com.cc.vendas.infraestrutura.configuracao;

import com.cc.vendas.aplicacao.casosdeuso.ConfirmarPagamentoUseCase;
import com.cc.vendas.aplicacao.casosdeuso.VeiculoUseCase;
import com.cc.vendas.aplicacao.casosdeuso.VendaEventPublisher;
import com.cc.vendas.aplicacao.casosdeuso.VendaUseCase;
import com.cc.vendas.aplicacao.servicos.PagamentoServiceImpl;
import com.cc.vendas.aplicacao.servicos.VeiculoServiceImpl;
import com.cc.vendas.aplicacao.servicos.VendaVeiculoServiceImpl;
import com.cc.vendas.dominio.pagamento.PagamentoRepository;
import com.cc.vendas.dominio.veiculo.VeiculoRepository;
import com.cc.vendas.dominio.venda.VendaVeiculoRepository;
import com.cc.vendas.infraestrutura.global.VendaUseCaseTransactional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ConfirmarPagamentoUseCase confirmarPagamentoUseCase(PagamentoRepository repository, VendaEventPublisher publisher) {
        return new PagamentoServiceImpl(repository, publisher);
    }

    @Bean
    public VeiculoUseCase veiculoUseCase(VeiculoRepository repository) {
        return new VeiculoServiceImpl(repository);
    }

    @Bean
    public VendaUseCase vendaUseCase(
            VeiculoRepository veiculoRepository,
            VendaVeiculoRepository vendaRepository) {

        VendaUseCase service =
                new VendaVeiculoServiceImpl(veiculoRepository, vendaRepository);

        return new VendaUseCaseTransactional(service);
    }
}
