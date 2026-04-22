package com.casesenha.validator;

import com.casesenha.validator.regra.ExcecaoRegraValidacao;
import com.casesenha.validator.regra.RegraValidacao;

import java.util.List;

public class ValidadorSenhaComposto implements ValidadorSenha {

    private final List<RegraValidacao> regras;

    public ValidadorSenhaComposto(List<RegraValidacao> regras) {
        if (regras == null || regras.isEmpty()) {
            throw new IllegalArgumentException("A lista de regras não pode ser nula ou vazia");
        }
        this.regras = List.copyOf(regras);
    }

    @Override
    public boolean ehValida(String senha) {
        return regras.stream().allMatch(regra -> validarSemPropagarExcecao(regra, senha));
    }

    private boolean validarSemPropagarExcecao(RegraValidacao regra, String senha) {
        try {
            return regra.validar(senha);
        } catch (ExcecaoRegraValidacao excecao) {
            return false;
        }
    }
}
