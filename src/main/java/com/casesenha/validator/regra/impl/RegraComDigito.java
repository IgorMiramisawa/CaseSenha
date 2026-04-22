package com.casesenha.validator.regra.impl;

import com.casesenha.validator.regra.ExcecaoRegraValidacao;
import com.casesenha.validator.regra.RegraValidacao;

public class RegraComDigito implements RegraValidacao {

    @Override
    public boolean validar(String senha) throws ExcecaoRegraValidacao {
        return senha.chars().anyMatch(Character::isDigit);
    }
}
