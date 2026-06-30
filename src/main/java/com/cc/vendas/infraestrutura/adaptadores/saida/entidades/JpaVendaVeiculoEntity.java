package com.cc.vendas.infraestrutura.adaptadores.saida.entidades;

import com.cc.vendas.shared.infrastructure.persistence.BaseJpaEntity;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_vendas_veiculo")
@Data
public class JpaVendaVeiculoEntity extends BaseJpaEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    private UUID idVeiculo;
    private Double preco;
    private String docComprador;
    private LocalDateTime dataCompra;

    protected JpaVendaVeiculoEntity() {}

    public JpaVendaVeiculoEntity(UUID idVeiculo, Double preco, String docComprador, LocalDateTime dataCompra) {
        this.id = UUID.randomUUID();
        this.idVeiculo = idVeiculo;
        this.preco = preco;
        this.docComprador = docComprador;
        this.dataCompra = dataCompra;
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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
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
