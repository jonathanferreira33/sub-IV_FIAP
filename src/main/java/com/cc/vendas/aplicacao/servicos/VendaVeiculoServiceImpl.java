package com.cc.vendas.aplicacao.servicos;

import com.cc.vendas.aplicacao.casosdeuso.VendaEventPublisher;
import com.cc.vendas.aplicacao.casosdeuso.VendaUseCase;
import com.cc.vendas.aplicacao.dto.mapper.VeiculoAppMapper;
import com.cc.vendas.aplicacao.dto.saida.VeiculoResumoOutput;
import com.cc.vendas.dominio.excecao.RegraNegocioException;
import com.cc.vendas.dominio.utils.GeneradorCodigoPagamento;
import com.cc.vendas.dominio.veiculo.Veiculo;
import com.cc.vendas.dominio.veiculo.VeiculoRepository;
import com.cc.vendas.dominio.venda.VendaVeiculo;
import com.cc.vendas.dominio.venda.VendaVeiculoRepository;
import com.cc.vendas.infraestrutura.adaptadores.saida.mensageria.PagamentoCriadoEvent;
import com.cc.vendas.infraestrutura.adaptadores.saida.mensageria.PixRequest;
import com.cc.vendas.shared.enums.PaymentMethod;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

public class VendaVeiculoServiceImpl implements VendaUseCase {

    private final VeiculoRepository veiculoRepositoryPort;
    private final VendaVeiculoRepository vendaRepositoryPort;
    private final VendaEventPublisher eventPublisher;

    public VendaVeiculoServiceImpl(
            VeiculoRepository veiculoRepositoryPort,
            VendaVeiculoRepository vendaRepositoryPort,
            VendaEventPublisher eventPublisher) {
        this.veiculoRepositoryPort = veiculoRepositoryPort;
        this.vendaRepositoryPort = vendaRepositoryPort;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public VeiculoResumoOutput registrarVenda(UUID id, String docComprador) {
        Veiculo veiculo = veiculoRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new RegraNegocioException("Veículo não encontrado"));

        veiculo.marcarComoVendido(docComprador);
        veiculoRepositoryPort.salvar(veiculo);

        String codPagamento = GeneradorCodigoPagamento.gerarCodigo();
        PixRequest dadosPix = new PixRequest(
                UUID.randomUUID(),
                "email@email.com.br",
                LocalDateTime.now().plusHours(2)
        );

        PagamentoCriadoEvent evento = new PagamentoCriadoEvent(
                veiculo.getPreco(),
                veiculo.getId(),
                "Pagamento referente à venda de veículo ID: " + veiculo.getId(),
                veiculo.getId(),
                PaymentMethod.PIX,
                null,
                dadosPix,
                codPagamento
        );

        eventPublisher.publicarVendaRegistrada(evento);

        VendaVeiculo venda = VendaVeiculo.criar(
                veiculo.getId(),
                veiculo.getPreco(),
                docComprador,
                codPagamento
        );
        vendaRepositoryPort.salvar(venda);

        return VeiculoAppMapper.veiculoParaResumoOutput(veiculo);
    }
}
