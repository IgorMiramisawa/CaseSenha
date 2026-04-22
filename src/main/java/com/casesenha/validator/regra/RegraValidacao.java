package com.casesenha.validator.regra;

public interface RegraValidacao {
    boolean validar(String senha) throws ExcecaoRegraValidacao;

    String descricao();
}
