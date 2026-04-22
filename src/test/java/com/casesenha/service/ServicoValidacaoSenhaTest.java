package com.casesenha.service;

import com.casesenha.validator.ValidadorSenha;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicoValidacaoSenhaTest {

    @Mock
    private ValidadorSenha validadorSenha;

    @InjectMocks
    private ServicoValidacaoSenha servicoValidacaoSenha;

    @Test
    void deveDelegarValidacaoParaValidador() {
        when(validadorSenha.ehValida("AbTp9!fok")).thenReturn(true);

        boolean resultado = servicoValidacaoSenha.validar("AbTp9!fok");

        assertThat(resultado).isTrue();
        verify(validadorSenha).ehValida("AbTp9!fok");
    }

    @Test
    void deveRetornarFalseQuandoSenhaForVazia() {
        when(validadorSenha.ehValida("")).thenReturn(false);

        boolean resultado = servicoValidacaoSenha.validar("");

        assertThat(resultado).isFalse();
        verify(validadorSenha).ehValida("");
    }
}
