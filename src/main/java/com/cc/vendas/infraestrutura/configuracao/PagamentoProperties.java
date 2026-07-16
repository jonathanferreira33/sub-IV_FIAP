package com.cc.vendas.infraestrutura.configuracao;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "integracao.pagamento")
public record PagamentoProperties(
        String baseUrl
) {
}
