package com.cc.vendas.infraestrutura.erro;

import com.cc.vendas.dominio.excecao.RegraNegocioException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        this.handler = new GlobalExceptionHandler();
        this.request = new MockHttpServletRequest();
        this.request.setRequestURI("/api/test-uri");
    }

    @Test
    void deveTratarEntityNotFoundERetornarStatus404() {
        EntityNotFoundException exception = new EntityNotFoundException("Veículo não encontrado");

        ResponseEntity<ApiErrorResponse> response = handler.handleEntityNotFound(exception, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().status());
        assertEquals("Recurso não encontrado", response.getBody().error());
        assertEquals("Veículo não encontrado", response.getBody().message());
        assertEquals("/api/test-uri", response.getBody().path());
    }

    @Test
    void deveTratarRegraNegocioERetornarStatus400() {
        RegraNegocioException exception = new RegraNegocioException("Preço não pode ser negativo");

        ResponseEntity<ApiErrorResponse> response = handler.handleRegraNegocio(exception, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().status());
        assertEquals("Erro de regra de negócio", response.getBody().error());
        assertEquals("Preço não pode ser negativo", response.getBody().message());
    }

    @Test
    void deveTratarTypeMismatchParaUUIDERetornarMensagemCustomizada() throws Exception {
        MethodParameter parameter = new MethodParameter(
                Object.class.getMethod("toString"), -1);

        MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(
                "texto-invalido", UUID.class, "idVeiculo", parameter, new IllegalArgumentException());

        ResponseEntity<ApiErrorResponse> response = handler.handleTypeMismatch(exception, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ID inválido", response.getBody().error());
        assertEquals("O identificador informado não é um UUID válido.", response.getBody().message());
    }

    @Test
    void deveTratarTypeMismatchParaOutrosTiposERetornarMensagemPadrao() throws Exception {
        MethodParameter parameter = new MethodParameter(
                Object.class.getMethod("toString"), -1);

        MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(
                "texto", Integer.class, "ano", parameter, new IllegalArgumentException());

        ResponseEntity<ApiErrorResponse> response = handler.handleTypeMismatch(exception, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Parâmetro inválido", response.getBody().error());
        assertEquals("Um ou mais parâmetros estão inválidos.", response.getBody().message());
    }

    @Test
    void deveTratarExceptionGenericaERetornarStatus500Mascarado() {
        Exception exception = new Exception("Erro interno de banco");

        ResponseEntity<ApiErrorResponse> response = handler.handleGenerica(exception, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().status());
        assertEquals("Erro interno", response.getBody().error());
        assertEquals("Erro inesperado. Contate o suporte.", response.getBody().message());
    }

    @Test
    void naoDeveMascararErroQuandoRotaForDoSwagger() {
        request.setRequestURI("/v3/api-docs/vendas");
        Exception exception = new Exception("Erro de parse do OpenAPI");

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> handler.handleGenerica(exception, request)
        );

        assertEquals(exception, thrown.getCause());
    }
}