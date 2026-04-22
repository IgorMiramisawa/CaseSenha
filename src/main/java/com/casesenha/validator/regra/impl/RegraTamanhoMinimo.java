package com.casesenha.validator.regra.impl;

import com.casesenha.validator.regra.ExcecaoRegraValidacao;
import com.casesenha.validator.regra.RegraValidacao;

public class RegraTamanhoMinimo implements RegraValidacao {

    private static final int TAMANHO_MINIMO = 9;

    @Override
    public boolean validar(String senha) throws ExcecaoRegraValidacao {
        validarSenhaNaoNula(senha);
        return senha.length() >= TAMANHO_MINIMO;
    }

    @Override
    public String descricao() {
        return "Deve possuir no mínimo %d caracteres".formatted(TAMANHO_MINIMO);
    }

    private void validarSenhaNaoNula(String senha) throws ExcecaoRegraValidacao {
        if (senha == null) {
            throw new ExcecaoRegraValidacao("Senha não pode ser nula");
        }
    }
}
