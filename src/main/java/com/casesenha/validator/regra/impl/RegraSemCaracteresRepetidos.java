package com.casesenha.validator.regra.impl;

import com.casesenha.validator.regra.ExcecaoRegraValidacao;
import com.casesenha.validator.regra.RegraValidacao;

import java.util.HashSet;
import java.util.Set;

public class RegraSemCaracteresRepetidos implements RegraValidacao {

    @Override
    public boolean validar(String senha) throws ExcecaoRegraValidacao {

        Set<Character> caracteres = new HashSet<>();
        for (char caractere : senha.toCharArray()) {
            if (!caracteres.add(caractere)) {
                return false;
            }
        }
        return true;
    }
}
