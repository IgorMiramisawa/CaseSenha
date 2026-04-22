package com.casesenha.validator.regra.impl;

import com.casesenha.validator.regra.ExcecaoRegraValidacao;
import com.casesenha.validator.regra.RegraValidacao;

public class RegraComDigito implements RegraValidacao {

    @Override
    public boolean validar(String senha) throws ExcecaoRegraValidacao {
        validarSenhaNaoNula(senha);
        return senha.chars().anyMatch(Character::isDigit);
    }

    @Override
    public String descricao() {
        return "Deve possuir ao menos um dígito";
    }

    private void validarSenhaNaoNula(String senha) throws ExcecaoRegraValidacao {
        if (senha == null) {
            throw new ExcecaoRegraValidacao("Senha não pode ser nula");
        }
    }
}
