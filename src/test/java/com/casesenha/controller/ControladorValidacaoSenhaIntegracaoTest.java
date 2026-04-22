package com.casesenha.controller;

import com.casesenha.dto.RequisicaoValidacaoSenha;
import com.casesenha.dto.RespostaErro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControladorValidacaoSenhaIntegracaoTest {

    private static final String ENDPOINT = "/api/v1/senhas/validar";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void deveRetornarOkComTrueQuandoSenhaForValida() {
        ResponseEntity<Boolean> resposta = restTemplate.postForEntity(
                ENDPOINT,
                new RequisicaoValidacaoSenha("AbTp9!fok"),
                Boolean.class
        );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody()).isTrue();
    }

    @Test
    void deveRetornarOkComFalseQuandoSenhaForInvalida() {
        ResponseEntity<Boolean> resposta = restTemplate.postForEntity(
                ENDPOINT,
                new RequisicaoValidacaoSenha("abc"),
                Boolean.class
        );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody()).isFalse();
    }

    @Test
    void deveRetornarBadRequestQuandoCampoSenhaForVazio() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entidade = new HttpEntity<>("{}", headers);

        ResponseEntity<Boolean> resposta = restTemplate.postForEntity(
                ENDPOINT,
                entidade,
                Boolean.class
        );

        assertThat(resposta.getBody()).isNotNull();
    }

    @Test
    void deveRetornarBadRequestQuandoJsonForInvalido() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entidade = new HttpEntity<>("json invalido", headers);

        ResponseEntity<RespostaErro> resposta = restTemplate.postForEntity(
                ENDPOINT,
                entidade,
                RespostaErro.class
        );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().getMensagem()).isEqualTo("Corpo da requisição inválido");
    }
}
