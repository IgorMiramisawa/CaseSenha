package com.casesenha.validator.regra.impl;

import com.casesenha.validator.regra.ExcecaoRegraValidacao;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RegrasValidacaoTest {

    @Test
    void deveValidarRegraDeTamanhoMinimo() throws ExcecaoRegraValidacao {
        RegraTamanhoMinimo regra = new RegraTamanhoMinimo();

        assertThat(regra.validar("123456789")).isTrue();
        assertThat(regra.validar("12345678")).isFalse();
    }

    @Test
    void deveValidarRegraSemCaracteresRepetidos() throws ExcecaoRegraValidacao {
        RegraSemCaracteresRepetidos regra = new RegraSemCaracteresRepetidos();

        assertThat(regra.validar("AbTp9!fok")).isTrue();
        assertThat(regra.validar("AbTp9!foo")).isFalse();
    }

    @Test
    void deveValidarRegraSemEspacosEmBranco() throws ExcecaoRegraValidacao {
        RegraSemEspacosEmBranco regra = new RegraSemEspacosEmBranco();

        assertThat(regra.validar("AbTp9!fok")).isTrue();
        assertThat(regra.validar("AbTp9 fok")).isFalse();
        assertThat(regra.validar("AbTp9\tfok")).isFalse();
    }

    @Test
    void deveValidarRegraComDigito() throws ExcecaoRegraValidacao {
        RegraComDigito regra = new RegraComDigito();

        assertThat(regra.validar("Abc1def")).isTrue();
        assertThat(regra.validar("AbcDef")).isFalse();
    }

    @Test
    void deveValidarRegraComLetraMinuscula() throws ExcecaoRegraValidacao {
        RegraComLetraMinuscula regra = new RegraComLetraMinuscula();

        assertThat(regra.validar("ABCd")).isTrue();
        assertThat(regra.validar("ABC123")).isFalse();
    }

    @Test
    void deveValidarRegraComLetraMaiuscula() throws ExcecaoRegraValidacao {
        RegraComLetraMaiuscula regra = new RegraComLetraMaiuscula();

        assertThat(regra.validar("abcD")).isTrue();
        assertThat(regra.validar("abc123")).isFalse();
    }

    @Test
    void deveValidarRegraComCaractereEspecial() throws ExcecaoRegraValidacao {
        RegraComCaractereEspecial regra = new RegraComCaractereEspecial();

        assertThat(regra.validar("Abc!def")).isTrue();
        assertThat(regra.validar("Abc?def")).isFalse();
    }
}
