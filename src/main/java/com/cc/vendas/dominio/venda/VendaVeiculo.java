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

    public VendaVeiculo() {
    }

    private VendaVeiculo(
            UUID id,
            UUID idVeiculo,
            BigDecimal preco,
            String docComprador,
            LocalDateTime dataVenda
    ) {
        this.id = id;
        this.idVeiculo = idVeiculo;
        this.preco = preco;
        this.docComprador = docComprador;
    }

    public VendaVeiculo(UUID idVeiculo, BigDecimal preco, String docComprador) {
        this.idVeiculo = idVeiculo;
        this.preco = preco;
        this.docComprador = docComprador;
    }

    public UUID getId() { return id; }
    public UUID getIdVeiculo() { return idVeiculo; }
    public BigDecimal getPreco() { return preco; }
    public String getDocComprador() { return docComprador; }
    public LocalDateTime getDataVenda() { return dataVenda; }

    public static VendaVeiculo criar(
            UUID idVeiculo,
            BigDecimal preco,
            String docComprador
    ) {
        return new VendaVeiculo(
                UUID.randomUUID(),
                idVeiculo,
                preco,
                docComprador,
                LocalDateTime.now()
        );
    }
}
