package com.cc.vendas.dominio.pagamento;

import com.cc.vendas.dominio.excecao.RegraNegocioException;
import com.cc.vendas.shared.StatusPagamento;

import java.time.Instant;
import java.util.UUID;

public class Pagamento {
    private UUID id;
    private UUID vendaId;
    private Double valor;
    private String codigoExterno;
    private StatusPagamento status;
    private Instant dataCriacao;
    private Instant dataAtualizacao;

    private Pagamento(UUID id, UUID vendaId, Double valor, String codigoPagamento, StatusPagamento status, Instant criadoEm, Instant atualizadoEm) {
        this.id = id;
        this.vendaId = vendaId;
        this.valor = valor;
        this.codigoExterno = codigoPagamento;
        this.status = status;
        this.dataCriacao = criadoEm;
        this.dataAtualizacao = atualizadoEm;
    }

    public UUID getId() { return id; }
    public UUID getVendaId() { return vendaId; }
    public Double getValor() { return valor; }
    public String getCodigoExterno() { return codigoExterno; }
    public StatusPagamento getStatus() { return status; }
    public Instant getDataCriacao() { return dataCriacao; }
    public Instant getDataAtualizacao() { return dataAtualizacao; }

    public static Pagamento criar(
            UUID vendaId,
            Double valor,
            String codigoPagamento
    ) {
        return new Pagamento(
                UUID.randomUUID(),
                vendaId,
                valor,
                codigoPagamento,
                StatusPagamento.PENDENTE,
                Instant.now(),
                Instant.now()
        );
    }

    public static Pagamento reconstituir(
            UUID id,
            UUID vendaId,
            Double valor,
            String codigoPagamento,
            StatusPagamento status,
            Instant criadoEm,
            Instant atualizadoEm
    ) {
        return new Pagamento(
                id,
                vendaId,
                valor,
                codigoPagamento,
                status,
                criadoEm,
                atualizadoEm
        );
    }

    public void atualizarStatus(StatusPagamento novoStatus) {
        if (!this.status.podeTransicionar(novoStatus))
            throw new RegraNegocioException("Transição inválida");

        this.status = novoStatus;
        this.dataAtualizacao = Instant.now();
    }

    public boolean estaPago() {
        return this.status == StatusPagamento.PAGO;
    }

    public void confirmar() {
        if (!status.podeTransicionar(StatusPagamento.PAGO)) {
            throw new IllegalStateException("Transição inválida de status");
        }
        this.status = StatusPagamento.PAGO;
    }

    public void cancelar() {
        if (!status.podeTransicionar(StatusPagamento.CANCELADO)) {
            throw new IllegalStateException("Transição inválida de status");
        }
        this.status = StatusPagamento.CANCELADO;
    }


}
