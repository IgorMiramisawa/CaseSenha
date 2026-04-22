package com.casesenha.controller;

import com.casesenha.dto.RequisicaoValidacaoSenha;
import com.casesenha.service.ServicoValidacaoSenha;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/senhas")
@AllArgsConstructor
public class ControladorValidacaoSenha {

    private final ServicoValidacaoSenha servicoValidacaoSenha;

    @PostMapping("/validar")
    public ResponseEntity<Boolean> validar(@RequestBody RequisicaoValidacaoSenha requisicao) {
        String senha = requisicao.getSenha();
        boolean valida = servicoValidacaoSenha.validar(senha);
        return ResponseEntity.ok(valida);
    }
}
