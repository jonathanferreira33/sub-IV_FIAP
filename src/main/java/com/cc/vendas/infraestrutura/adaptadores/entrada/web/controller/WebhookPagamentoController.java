package com.cc.vendas.infraestrutura.adaptadores.entrada.web.controller;

import com.cc.vendas.infraestrutura.adaptadores.entrada.web.dto.requisicao.WebhookPagementoRequest;
import com.cc.vendas.aplicacao.casosdeuso.ConfirmarPagamentoUseCase;
import com.cc.vendas.aplicacao.dto.entrada.ConfirmacaoPagamentoInput;
import com.cc.vendas.dominio.excecao.RegraNegocioException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("webhook/pagamentos")
public class WebhookPagamentoController {

    private final ConfirmarPagamentoUseCase useCase;

    public WebhookPagamentoController(ConfirmarPagamentoUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<Void> receber (@Valid @RequestBody WebhookPagementoRequest request) {

        try {
            ConfirmacaoPagamentoInput input = new ConfirmacaoPagamentoInput(
                    request.vendaId(),
                    request.valor(),
                    request.codigoPagamento(),
                    request.statusPagamento()
            );

            useCase.confirmar(input);

            return ResponseEntity.ok().build();
        } catch (RegraNegocioException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
