package com.casesenha.validator.regra.impl;

import com.casesenha.validator.regra.ExcecaoRegraValidacao;
import com.casesenha.validator.regra.RegraValidacao;

public class RegraComCaractereEspecial implements RegraValidacao {

    private static final String CARACTERES_ESPECIAIS = "!@#$%^&*()-+";

    @Override
    public boolean validar(String senha) throws ExcecaoRegraValidacao {
        validarSenhaNaoNula(senha);
        return senha.chars().anyMatch(caractere -> CARACTERES_ESPECIAIS.indexOf(caractere) >= 0);
    }

    @Override
    public String descricao() {
        return "Deve possuir ao menos um caractere especial válido";
    }

    private void validarSenhaNaoNula(String senha) throws ExcecaoRegraValidacao {
        if (senha == null) {
            throw new ExcecaoRegraValidacao("Senha não pode ser nula");
        }
    }
}
