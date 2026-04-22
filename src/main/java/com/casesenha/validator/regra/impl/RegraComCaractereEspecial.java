package com.casesenha.validator.regra.impl;

import com.casesenha.validator.regra.ExcecaoRegraValidacao;
import com.casesenha.validator.regra.RegraValidacao;

public class RegraComCaractereEspecial implements RegraValidacao {

    private static final String CARACTERES_ESPECIAIS = "!@#$%^&*()-+";

    @Override
    public boolean validar(String senha) throws ExcecaoRegraValidacao {
        return senha.chars().anyMatch(caractere -> CARACTERES_ESPECIAIS.indexOf(caractere) >= 0);
    }

}
