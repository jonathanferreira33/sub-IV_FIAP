package com.cc.vendas.infraestrutura.adaptadores.saida.entidades;

import com.cc.vendas.shared.StatusPagamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tb_pagamentos")
@Data
public class JpaPagamentoEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    private UUID vendaId;

    private BigDecimal valor;
    private String codigoPagamento;
    private StatusPagamento status;
    private Instant dataCriacao;
    private Instant dataUltimaAtualizacao;

    protected JpaPagamentoEntity() {}

    public JpaPagamentoEntity(
            UUID id,
            UUID vendaId,
            BigDecimal valor,
            String codigoPagamento,
            StatusPagamento status,
            Instant dataCriacao,
            Instant dataUltimaAtualizacao
    ) {
        this.id = id;
        this.vendaId = vendaId;
        this.valor = valor;
        this.codigoPagamento = codigoPagamento;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    public UUID getVendaId() { return vendaId; }

    public void setVendaId(UUID vendaId) { this.vendaId = vendaId; }

    public BigDecimal getValor() { return valor; }

    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getCodigoPagamento() { return codigoPagamento; }

    public void setCodigoPagamento(String codigoPagamento) {
        this.codigoPagamento = codigoPagamento;
    }

    public StatusPagamento getStatus() { return status; }

    public void setStatus(StatusPagamento status) { this.status = status; }

    public Instant getDataCriacao() { return dataCriacao; }

    public void setDataCriacao(Instant dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Instant getDataUltimaAtualizacao() { return dataUltimaAtualizacao; }

    public void setDataUltimaAtualizacao(Instant dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }
}
