package com.casesenha.exception;

import com.casesenha.dto.RespostaErro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControladorExcecaoGlobal {

    private static final Logger log = LoggerFactory.getLogger(ControladorExcecaoGlobal.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RespostaErro> tratarIllegalArgumentException(
            IllegalArgumentException excecao,
            WebRequest requisicao) {

        log.warn("Falha de validação da requisição: {}", excecao.getMessage());

        RespostaErro respostaErro = new RespostaErro(
                HttpStatus.BAD_REQUEST.value(),
                "Argumento inválido informado",
                excecao.getMessage(),
                requisicao.getDescription(false).replace("uri=", "")
        );

        return ResponseEntity.badRequest().body(respostaErro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RespostaErro> tratarHttpMessageNotReadableException(
            HttpMessageNotReadableException excecao,
            WebRequest requisicao) {

        log.warn("Corpo da requisição inválido: {}", excecao.getMessage());

        String mensagemErro = excecao.getMostSpecificCause() != null
                ? excecao.getMostSpecificCause().getMessage()
                : "Não foi possível interpretar o JSON enviado";

        RespostaErro respostaErro = new RespostaErro(
                HttpStatus.BAD_REQUEST.value(),
                "Corpo da requisição inválido",
                mensagemErro,
                requisicao.getDescription(false).replace("uri=", "")
        );

        return ResponseEntity.badRequest().body(respostaErro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespostaErro> tratarException(
            Exception excecao,
            WebRequest requisicao) {

        log.error("Erro inesperado ao processar requisição", excecao);

        RespostaErro respostaErro = new RespostaErro(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno ao processar a requisição",
                excecao.getMessage(),
                requisicao.getDescription(false).replace("uri=", "")
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respostaErro);
    }
}
