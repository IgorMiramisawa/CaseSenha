package com.casesenha.validator.regra.impl;

import com.casesenha.validator.regra.ExcecaoRegraValidacao;
import com.casesenha.validator.regra.RegraValidacao;

import java.util.HashSet;
import java.util.Set;

public class RegraSemCaracteresRepetidos implements RegraValidacao {

    @Override
    public boolean validar(String senha) throws ExcecaoRegraValidacao {
        validarSenhaNaoNula(senha);

        Set<Character> caracteres = new HashSet<>();
        for (char caractere : senha.toCharArray()) {
            if (!caracteres.add(caractere)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String descricao() {
        return "Não deve possuir caracteres repetidos";
    }

    private void validarSenhaNaoNula(String senha) throws ExcecaoRegraValidacao {
        if (senha == null) {
            throw new ExcecaoRegraValidacao("Senha não pode ser nula");
        }
    }
}
