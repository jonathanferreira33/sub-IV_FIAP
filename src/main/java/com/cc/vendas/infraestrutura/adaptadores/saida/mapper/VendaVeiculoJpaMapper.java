package com.cc.vendas.infraestrutura.adaptadores.saida.mapper;
import com.cc.vendas.infraestrutura.adaptadores.saida.entidades.JpaVendaVeiculoEntity;
import com.cc.vendas.dominio.venda.VendaVeiculo;

public class VendaVeiculoJpaMapper {

    public static JpaVendaVeiculoEntity dominioParaJpa(VendaVeiculo veiculo) {
        return new JpaVendaVeiculoEntity(
                veiculo.getIdVeiculo(),
                veiculo.getPreco(),
                veiculo.getDocComprador(),
                veiculo.getDataVenda()
        );
    }

    public static VendaVeiculo jpaParaDominio(JpaVendaVeiculoEntity entidade) {
        if (entidade == null) {
            throw new IllegalArgumentException("Entidade JPA de Venda Veiculo não pode ser nula");
        }

        return VendaVeiculo.criar(
                entidade.getIdVeiculo(),
                entidade.getPreco(),
                entidade.getDocComprador()
        );
    }
}
