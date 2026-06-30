package com.cc.vendas.infraestrutura.erro;

import com.cc.vendas.dominio.excecao.RegraNegocioException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.OffsetDateTime;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEntityNotFound(
            EntityNotFoundException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Recurso não encontrado",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ApiErrorResponse> handleRegraNegocio(
            RegraNegocioException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Erro de regra de negócio",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        if (ex.getRequiredType() != null &&
                ex.getRequiredType().equals(UUID.class)) {

            return buildResponse(
                    HttpStatus.BAD_REQUEST,
                    "ID inválido",
                    "O identificador informado não é um UUID válido.",
                    request
            );
        }

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Parâmetro inválido",
                "Um ou mais parâmetros estão inválidos.",
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenerica(
            Exception ex,
            HttpServletRequest request) {

        String path = request.getRequestURI();

        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) {
            throw new RuntimeException(ex);
        }

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno",
                "Erro inesperado. Contate o suporte.",
                request
        );
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(
            HttpStatus status,
            String title,
            String detail,
            HttpServletRequest request) {

        ApiErrorResponse erro = new ApiErrorResponse(
                OffsetDateTime.now().toString(),
                status.value(),
                title,
                detail,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(erro);
    }
}
