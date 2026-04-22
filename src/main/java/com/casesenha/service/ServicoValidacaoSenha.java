package com.casesenha.service;

import com.casesenha.validator.ValidadorSenha;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServicoValidacaoSenha {

    private static final Logger log = LoggerFactory.getLogger(ServicoValidacaoSenha.class);

    private final ValidadorSenha validadorSenha;

    public boolean validar(String senha) {
        boolean valida = validadorSenha.ehValida(senha);
        log.info("Resultado da validação de senha: {}", valida ? "VÁLIDA" : "INVÁLIDA");
        return valida;
    }
}
