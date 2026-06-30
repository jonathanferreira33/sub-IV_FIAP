package com.cc.vendas.infraestrutura.adaptadores.saida.mapper;

import com.cc.vendas.infraestrutura.adaptadores.saida.entidades.JpaPagamentoEntity;
import com.cc.vendas.dominio.pagamento.Pagamento;

public class PagamentoMapper {

    public static JpaPagamentoEntity dominioParaJpa(Pagamento dominio) {
        return new JpaPagamentoEntity(
                dominio.getId(),
                dominio.getVendaId(),
                dominio.getValor(),
                dominio.getCodigoExterno(),
                dominio.getStatus(),
                dominio.getDataCriacao(),
                dominio.getDataAtualizacao()
        );

    }

    public static Pagamento jpaParaDominio(JpaPagamentoEntity entidade) {
        if (entidade == null)
            throw new IllegalArgumentException("Entidade JPA de pagamento não pode ser nula");

        return Pagamento.reconstituir(
                entidade.getId(),
                entidade.getVendaId(),
                entidade.getValor(),
                entidade.getCodigoPagamento(),
                entidade.getStatus(),
                entidade.getDataCriacao(),
                entidade.getDataUltimaAtualizacao()
        );
    }
}
