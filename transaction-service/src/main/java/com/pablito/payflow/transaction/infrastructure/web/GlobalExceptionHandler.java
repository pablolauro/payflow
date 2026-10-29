package com.pablito.payflow.transaction.infrastructure.web;

import com.pablito.payflow.transaction.application.TransacaoNaoEncontradaException;
import com.pablito.payflow.transaction.domain.TransacaoInvalidaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TransacaoNaoEncontradaException.class)
    public ProblemDetail naoEncontrada(TransacaoNaoEncontradaException ex) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problema.setTitle("Transação não encontrada");
        return problema;
    }

    @ExceptionHandler(TransacaoInvalidaException.class)
    public ProblemDetail invalida(TransacaoInvalidaException ex) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage());
        problema.setTitle("Transação inválida");
        return problema;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validacao(MethodArgumentNotValidException ex) {
        ProblemDetail problema = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problema.setTitle("Dados inválidos");
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(erro -> erros.put(erro.getField(), erro.getDefaultMessage()));
        problema.setProperty("campos", erros);
        return problema;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail malformado(HttpMessageNotReadableException ex) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "O corpo da requisição é inválido ou contém valores não aceitos.");
        problema.setTitle("Requisição inválida");
        return problema;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail tipoIncorreto(MethodArgumentTypeMismatchException ex) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "O parâmetro '" + ex.getName() + "' tem um formato inválido.");
        problema.setTitle("Formato de argumento inválido");
        return problema;
    }

    @ExceptionHandler(Exception.class)
    public  ProblemDetail excecaoGenerica(Exception ex) {
        if (ex instanceof ErrorResponse erroDoSpring) {
            return erroDoSpring.getBody();
        }
        log.error("Erro inesperado na aplicação",ex);
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno na aplicação");
        problema.setTitle("Erro interno");
        return problema;
    }
}
