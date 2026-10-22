package com.pablito.payflow.transaction.infrastructure.config;

import com.pablito.payflow.transaction.application.CriarTransacaoService;
import com.pablito.payflow.transaction.application.port.TransacaoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransacaoConfig {

    @Bean
    public CriarTransacaoService criarTransacaoService(TransacaoRepository transacaoRepository){
        return new CriarTransacaoService(transacaoRepository);
    }

}
