package com.cc.vendas.dominio.pagamento;

import com.cc.vendas.dominio.excecao.RegraNegocioException;
import com.cc.vendas.shared.StatusPagamento;
import com.cc.vendas.shared.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Pagamento {
    private UUID id;
    private UUID vendaId;
    private BigDecimal valor;
    private String codigoExterno;
    private StatusPagamento status;
    private Instant dataCriacao;
    private Instant dataAtualizacao;
    private UUID customerId;
    private UUID cardId;
    private UUID pixId;
    private PaymentMethod paymentMethod;

    private Pagamento(UUID id, UUID vendaId, BigDecimal valor, String codigoPagamento, StatusPagamento status, Instant criadoEm, Instant atualizadoEm) {
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
    public BigDecimal getValor() { return valor; }
    public String getCodigoExterno() { return codigoExterno; }
    public StatusPagamento getStatus() { return status; }
    public Instant getDataCriacao() { return dataCriacao; }
    public Instant getDataAtualizacao() { return dataAtualizacao; }
    public UUID getCustomerId() { return customerId; }
    public UUID getCardId() { return cardId; }
    public UUID getPixId() { return pixId; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }

    public static Pagamento criar(
            UUID vendaId,
            BigDecimal valor,
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
            BigDecimal valor,
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
        return this.status == StatusPagamento.CONFIRMADO;
    }

    public void confirmar() {
        if (!status.podeTransicionar(StatusPagamento.CONFIRMADO)) {
            throw new IllegalStateException("Transição inválida de status");
        }
        this.status = StatusPagamento.CONFIRMADO;
    }

    public void cancelar() {
        if (!status.podeTransicionar(StatusPagamento.CANCELADO)) {
            throw new IllegalStateException("Transição inválida de status");
        }
        this.status = StatusPagamento.CANCELADO;
    }

}
