package com.casesenha.service;

import com.casesenha.validator.ValidadorSenha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ServicoValidacaoSenha {

    private static final Logger log = LoggerFactory.getLogger(ServicoValidacaoSenha.class);

    private final ValidadorSenha validadorSenha;

    public ServicoValidacaoSenha(ValidadorSenha validadorSenha) {
        this.validadorSenha = validadorSenha;
    }

    public boolean validar(String senha) {

        boolean valida = validadorSenha.ehValida(senha);
        log.info("Resultado da validação de senha: {}", valida ? "VÁLIDA" : "INVÁLIDA");
        return valida;
    }
}
