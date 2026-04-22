package com.casesenha.config;

import com.casesenha.validator.ValidadorSenha;
import com.casesenha.validator.ValidadorSenhaComposto;
import com.casesenha.validator.regra.RegraValidacao;
import com.casesenha.validator.regra.impl.RegraComCaractereEspecial;
import com.casesenha.validator.regra.impl.RegraComDigito;
import com.casesenha.validator.regra.impl.RegraComLetraMaiuscula;
import com.casesenha.validator.regra.impl.RegraComLetraMinuscula;
import com.casesenha.validator.regra.impl.RegraSemCaracteresRepetidos;
import com.casesenha.validator.regra.impl.RegraSemEspacosEmBranco;
import com.casesenha.validator.regra.impl.RegraTamanhoMinimo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConfiguracaoAplicacao {

    @Bean
    public List<RegraValidacao> regrasValidacaoSenha() {
        return List.of(
                new RegraTamanhoMinimo(),
                new RegraSemEspacosEmBranco(),
                new RegraSemCaracteresRepetidos(),
                new RegraComDigito(),
                new RegraComLetraMinuscula(),
                new RegraComLetraMaiuscula(),
                new RegraComCaractereEspecial()
        );
    }

    @Bean
    public ValidadorSenha validadorSenha(List<RegraValidacao> regrasValidacaoSenha) {
        return new ValidadorSenhaComposto(regrasValidacaoSenha);
    }
}
