package com.casesenha.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespostaErro {
    private int status;
    private String mensagem;
    private String erro;
    private LocalDateTime dataHora;
    private String caminho;

    public RespostaErro(int status, String mensagem, String erro, String caminho) {
        this.status = status;
        this.mensagem = mensagem;
        this.erro = erro;
        this.dataHora = LocalDateTime.now();
        this.caminho = caminho;
    }
}
