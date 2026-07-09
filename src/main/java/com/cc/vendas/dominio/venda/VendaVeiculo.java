package com.cc.vendas.dominio.venda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class VendaVeiculo {
    private UUID id;
    private UUID idVeiculo;
    private BigDecimal preco;
    private String docComprador;
    private LocalDateTime dataVenda;
    private String codigoPagamento;

    public VendaVeiculo() {
    }

    private VendaVeiculo(
            UUID id,
            UUID idVeiculo,
            BigDecimal preco,
            String docComprador,
            LocalDateTime dataVenda,
            String codigoPagamento
    ) {
        this.id = id;
        this.idVeiculo = idVeiculo;
        this.preco = preco;
        this.docComprador = docComprador;
        this.dataVenda = dataVenda;
        this.codigoPagamento = codigoPagamento;
    }

    public VendaVeiculo(UUID idVeiculo, BigDecimal preco, String docComprador, String codigoPagamento) {
        this.idVeiculo = idVeiculo;
        this.preco = preco;
        this.docComprador = docComprador;
        this.codigoPagamento = codigoPagamento;
    }

    public UUID getId() { return id; }
    public UUID getIdVeiculo() { return idVeiculo; }
    public BigDecimal getPreco() { return preco; }
    public String getDocComprador() { return docComprador; }
    public LocalDateTime getDataVenda() { return dataVenda; }
    public String getCodigoPagamento() { return codigoPagamento; }

    public static VendaVeiculo criar(
            UUID idVeiculo,
            BigDecimal preco,
            String docComprador,
            String codigoPagamento
    ) {
        return new VendaVeiculo(
                UUID.randomUUID(),
                idVeiculo,
                preco,
                docComprador,
                LocalDateTime.now(),
                codigoPagamento
        );
    }
}
