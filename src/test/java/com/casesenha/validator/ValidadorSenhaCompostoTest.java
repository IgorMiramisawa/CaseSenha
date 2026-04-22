package com.casesenha.validator;

import com.casesenha.validator.regra.RegraValidacao;
import com.casesenha.validator.regra.impl.RegraComCaractereEspecial;
import com.casesenha.validator.regra.impl.RegraComDigito;
import com.casesenha.validator.regra.impl.RegraComLetraMaiuscula;
import com.casesenha.validator.regra.impl.RegraComLetraMinuscula;
import com.casesenha.validator.regra.impl.RegraSemCaracteresRepetidos;
import com.casesenha.validator.regra.impl.RegraSemEspacosEmBranco;
import com.casesenha.validator.regra.impl.RegraTamanhoMinimo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidadorSenhaCompostoTest {

    private ValidadorSenha validadorSenha;

    @BeforeEach
    void setUp() {
        List<RegraValidacao> regras = List.of(
                new RegraTamanhoMinimo(),
                new RegraSemEspacosEmBranco(),
                new RegraSemCaracteresRepetidos(),
                new RegraComDigito(),
                new RegraComLetraMinuscula(),
                new RegraComLetraMaiuscula(),
                new RegraComCaractereEspecial()
        );
        validadorSenha = new ValidadorSenhaComposto(regras);
    }

    @Test
    void deveRetornarTrueQuandoSenhaAtenderTodasAsRegras() {
        assertThat(validadorSenha.ehValida("AbTp9!fok")).isTrue();
        assertThat(validadorSenha.ehValida("Abc123!@d")).isTrue();
        assertThat(validadorSenha.ehValida("Valid1@B+")) .isTrue();
    }

    @Test
    void deveRetornarFalseQuandoSenhaForInvalida() {
        assertThat(validadorSenha.ehValida("Abc1!")).isFalse();
        assertThat(validadorSenha.ehValida("AbTp!foA")).isFalse();
        assertThat(validadorSenha.ehValida("ABT9!FOK")).isFalse();
        assertThat(validadorSenha.ehValida("abt9!fok")).isFalse();
        assertThat(validadorSenha.ehValida("AbTp9fok")).isFalse();
        assertThat(validadorSenha.ehValida("AbTp9!foo")).isFalse();
        assertThat(validadorSenha.ehValida("AbTp9 fok")).isFalse();
        assertThat(validadorSenha.ehValida("")).isFalse();
        assertThat(validadorSenha.ehValida(null)).isFalse();
    }

    @Test
    void deveLancarExcecaoQuandoListaDeRegrasForNulaOuVazia() {
        assertThatThrownBy(() -> new ValidadorSenhaComposto(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A lista de regras não pode ser nula ou vazia");

        assertThatThrownBy(() -> new ValidadorSenhaComposto(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A lista de regras não pode ser nula ou vazia");
    }
}
