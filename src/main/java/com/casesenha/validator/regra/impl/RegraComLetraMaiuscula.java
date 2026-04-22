package com.casesenha.validator.regra.impl;

import com.casesenha.validator.regra.ExcecaoRegraValidacao;
import com.casesenha.validator.regra.RegraValidacao;

public class RegraComLetraMaiuscula implements RegraValidacao {

    @Override
    public boolean validar(String senha) throws ExcecaoRegraValidacao {
        validarSenhaNaoNula(senha);
        return senha.chars().anyMatch(Character::isUpperCase);
    }

    @Override
    public String descricao() {
        return "Deve possuir ao menos uma letra maiúscula";
    }

    private void validarSenhaNaoNula(String senha) throws ExcecaoRegraValidacao {
        if (senha == null) {
            throw new ExcecaoRegraValidacao("Senha não pode ser nula");
        }
    }
}
