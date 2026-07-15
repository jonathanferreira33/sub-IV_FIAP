package com.cc.vendas.dominio.veiculo;

import com.cc.vendas.dominio.excecao.RegraNegocioException;
import com.cc.vendas.dominio.valueobjects.Cor;
import com.cc.vendas.dominio.valueobjects.Marca;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Veiculo {
    private UUID id;
    private Marca marca;
    private String modelo;
    private Cor cor;
    private Integer ano;
    private BigDecimal preco;
    private StatusVeiculo status;
    private String docComprador;
    private Instant dataVenda;

    public Veiculo() {
        this.status = StatusVeiculo.ANALISE;
    }

    private Veiculo(
            UUID id,
            Marca marca,
            String modelo,
            Cor cor,
            Integer ano,
            BigDecimal preco
    ) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.ano = ano;
        this.preco = preco;
        this.status = StatusVeiculo.ANALISE;
    }

    public UUID getId() { return id; }
    public Marca getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public Cor getCor() { return cor; }
    public Integer getAno() { return ano; }
    public BigDecimal getPreco() { return preco; }
    public StatusVeiculo getStatus() { return status; }
    public String getDocComprador() { return docComprador; }
    public Instant getDataVenda() { return dataVenda; }

    private void setStatus(StatusVeiculo status) {
        this.status = status;
    }

    public static Veiculo criar(
            String marcaRaw,
            String modelo,
            String corRaw,
            Integer ano,
            BigDecimal preco
    ) {
        Cor corObjeto = new Cor(corRaw);
        Marca marcaObjeto = new Marca(marcaRaw);
        validarCampos(marcaObjeto, modelo, corObjeto, ano, preco);
        return new Veiculo(
                UUID.randomUUID(),
                marcaObjeto,
                modelo,
                corObjeto,
                ano,
                preco
        );
    }

    public static Veiculo reconstituir(
            UUID id,
            String marcaRaw,
            String modelo,
            String corRaw,
            Integer ano,
            BigDecimal preco,
            StatusVeiculo status,
            String docComprador,
            Instant dataVenda
    ) {
        Veiculo veiculo = new Veiculo(
                id,
                new Marca(marcaRaw),
                modelo,
                new Cor(corRaw),
                ano,
                preco
        );

        veiculo.status = status;
        veiculo.docComprador = docComprador;

        if (dataVenda != null)
            veiculo.dataVenda = dataVenda;

        return veiculo;
    }

    public void atualizarDados(
            String marcaRaw,
            String modelo,
            String corRaw,
            Integer ano,
            BigDecimal preco
    ) {
        validarEdicao();
        Cor novaCor = new Cor(corRaw);
        Marca novaMarca = new Marca(marcaRaw);
        validarCampos(novaMarca, modelo, novaCor, ano, preco);

        this.marca = novaMarca;
        this.modelo = modelo;
        this.cor = novaCor;
        this.ano = ano;
        this.preco = preco;
    }

    public void alterarStatusParaDisponivel() {
        setStatus(StatusVeiculo.DISPONIVEL_PARA_VENDA);
    }
    public void alterarStatusParaVendido() { setStatus(StatusVeiculo.VENDIDO); }

    public void marcarComoVendido(String cpf) {
        if (this.status != StatusVeiculo.DISPONIVEL_PARA_VENDA)
            throw new RegraNegocioException("Veiculo indisponivel para venda");

        this.docComprador = cpf;
        this.status = StatusVeiculo.VENDIDO;
        this.dataVenda = Instant.now();
    }

    public void validarEdicao() {
        if (this.status == StatusVeiculo.VENDIDO)
            throw new RegraNegocioException("Veiculo indisponivel para edição");
    }

    private static void validarCampos(
            Marca marca,
            String modelo,
            Cor cor,
            Integer ano,
            BigDecimal preco
    ) {
        if (marca == null)
            throw new RegraNegocioException("Marca obrigatória");

        if (modelo == null || modelo.isBlank())
            throw new RegraNegocioException("Modelo obrigatório");

        if (cor == null)
            throw new RegraNegocioException("Cor obrigatória");

        if (ano == null || ano <= 0)
            throw new RegraNegocioException("Ano inválido");

        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0)
            throw new RegraNegocioException("Preço inválido");
    }
}
