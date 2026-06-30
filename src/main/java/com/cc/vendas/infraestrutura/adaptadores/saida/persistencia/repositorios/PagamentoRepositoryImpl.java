package com.cc.vendas.infraestrutura.adaptadores.saida.persistencia.repositorios;

import com.cc.vendas.infraestrutura.adaptadores.saida.entidades.JpaPagamentoEntity;
import com.cc.vendas.infraestrutura.adaptadores.saida.mapper.PagamentoMapper;
import com.cc.vendas.dominio.pagamento.Pagamento;
import com.cc.vendas.dominio.pagamento.PagamentoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PagamentoRepositoryImpl implements PagamentoRepository {

    private final JpaPagamentoRepository repository;

    public PagamentoRepositoryImpl(JpaPagamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pagamento cadastrarPagamento(Pagamento pagamento) {
        JpaPagamentoEntity entity = PagamentoMapper.dominioParaJpa(pagamento);
        this.repository.save(entity);
        return PagamentoMapper.jpaParaDominio(entity);
    }

    @Override
    public Optional<Pagamento> buscarPagamentoPorId(UUID id) {
        return repository.findById(id)
                .map(PagamentoMapper::jpaParaDominio);
    }

    @Override
    public Optional<Pagamento> buscarPagamentoPorCodPagamento(String codPagamento) {
        JpaPagamentoEntity pagamento = repository.findByCodigoPagamento(codPagamento);

        return pagamento != null ? Optional.of(PagamentoMapper.jpaParaDominio(pagamento)) : Optional.empty();
    }

    @Override
    public void salvar(Pagamento pagamento) {
        JpaPagamentoEntity pagamentoEntity = PagamentoMapper.dominioParaJpa(pagamento);
        repository.save(pagamentoEntity);
    }
}
