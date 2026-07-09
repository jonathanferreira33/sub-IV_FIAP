package com.cc.vendas.infraestrutura.adaptadores.saida.entidades;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tb_veiculos")
public class JpaVeiculoEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    private String marca;
    private String modelo;
    private String cor;
    private Integer ano;
    private BigDecimal preco;
    private String statusVeiculo;
    private Instant dataVenda;

    @Column
    private String docComprador;

    protected JpaVeiculoEntity() { }

    public JpaVeiculoEntity(
            UUID id,
            String marca,
            String modelo,
            String cor,
            Integer ano,
            BigDecimal preco,
            String statusVeiculo,
            String docComprador,
            Instant dataVenda) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.ano = ano;
        this.preco = preco;
        this.statusVeiculo = statusVeiculo;
        this.docComprador = docComprador;
        this.dataVenda = dataVenda;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getStatusVeiculo() {
        return statusVeiculo;
    }

    public void setStatusVeiculo(String statusVeiculo) {
        this.statusVeiculo = statusVeiculo;
    }

    public String getDocComprador() {
        return docComprador;
    }

    public void setDocComprador(String docComprador) {
        this.docComprador = docComprador;
    }

    public Instant getDataVenda() { return dataVenda; }

    public void setDataVenda(Instant dataVenda) { this.dataVenda = dataVenda; }
}
