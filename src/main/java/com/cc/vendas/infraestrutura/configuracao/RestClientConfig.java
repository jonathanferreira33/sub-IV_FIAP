package com.cc.vendas.infraestrutura.configuracao;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties({PagamentoProperties.class})
public class RestClientConfig {

    @Bean
    public RestClient pagamentoRestClient(PagamentoProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.baseUrl())
                .build();
    }
}
