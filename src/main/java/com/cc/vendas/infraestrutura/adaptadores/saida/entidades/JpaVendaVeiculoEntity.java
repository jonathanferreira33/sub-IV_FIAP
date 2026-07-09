package com.cc.vendas.infraestrutura.adaptadores.saida.entidades;

import com.cc.vendas.shared.infrastructure.persistence.BaseJpaEntity;
import jakarta.persistence.*;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_vendas_veiculo")
public class JpaVendaVeiculoEntity extends BaseJpaEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    private UUID idVeiculo;
    private BigDecimal preco;
    private String docComprador;
    private LocalDateTime dataCompra;
    private String codigoPagamento;

    protected JpaVendaVeiculoEntity() {}

    public JpaVendaVeiculoEntity(
            UUID idVeiculo,
            BigDecimal preco,
            String docComprador,
            LocalDateTime dataCompra,
            String codigoPagamento) {
        this.id = UUID.randomUUID();
        this.idVeiculo = idVeiculo;
        this.preco = preco;
        this.docComprador = docComprador;
        this.dataCompra = dataCompra;
        this.codigoPagamento = codigoPagamento;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getIdVeiculo() {
        return idVeiculo;
    }
    public void setIdVeiculo(UUID idVeiculo) {
        this.idVeiculo = idVeiculo;
    }
    public BigDecimal getPreco() { return preco; }
    public String getCodigoPagamento() { return codigoPagamento; }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getDocComprador() {
        return docComprador;
    }

    public void setDocComprador(String docComprador) {
        this.docComprador = docComprador;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDateTime dataCompra) {
        this.dataCompra = dataCompra;
    }
}
