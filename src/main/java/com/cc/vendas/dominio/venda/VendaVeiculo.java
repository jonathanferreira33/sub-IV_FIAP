package com.cc.vendas.dominio.venda;

import java.time.LocalDateTime;
import java.util.UUID;

public class VendaVeiculo {
    private UUID id;
    private UUID idVeiculo;
    private Double preco;
    private String docComprador;
    private LocalDateTime dataVenda;

    public VendaVeiculo() {
    }

    private VendaVeiculo(
            UUID id,
            UUID idVeiculo,
            Double preco,
            String docComprador,
            LocalDateTime dataVenda
    ) {
        this.id = id;
        this.idVeiculo = idVeiculo;
        this.preco = preco;
        this.docComprador = docComprador;
    }

    public VendaVeiculo(UUID idVeiculo, Double preco, String docComprador) {
        this.idVeiculo = idVeiculo;
        this.preco = preco;
        this.docComprador = docComprador;
    }

    public UUID getId() { return id; }
    public UUID getIdVeiculo() { return idVeiculo; }
    public Double getPreco() { return preco; }
    public String getDocComprador() { return docComprador; }
    public LocalDateTime getDataVenda() { return dataVenda; }

    public static VendaVeiculo criar(
            UUID idVeiculo,
            Double preco,
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
