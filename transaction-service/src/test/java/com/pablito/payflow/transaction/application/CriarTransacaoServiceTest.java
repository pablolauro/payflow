package com.pablito.payflow.transaction.application;

import com.pablito.payflow.transaction.application.port.TransacaoRepository;
import com.pablito.payflow.transaction.domain.MeioPagamento;
import com.pablito.payflow.transaction.domain.StatusTransacao;
import com.pablito.payflow.transaction.domain.Transacao;
import com.pablito.payflow.transaction.domain.TransacaoInvalidaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class CriarTransacaoServiceTest {

    @Mock
    private TransacaoRepository repository;

    @InjectMocks
    private CriarTransacaoService service;

    @Test
    void deveCriarESalvarTransacao() {
        var command = new CriarTransacaoCommand("loja-123",new BigDecimal("19.99"), MeioPagamento.CREDITO);

        Transacao transacao = service.executar(command);

        verify(repository).salvar(transacao);
        assertThat(transacao.getStatus()).isEqualTo(StatusTransacao.RECEBIDA);
    }

    @Test
    void naoDeveSalvarTransacaoInvalida(){
        var command = new CriarTransacaoCommand("loja-123",new BigDecimal("-19.99"), MeioPagamento.CREDITO);

        assertThatThrownBy(() -> service.executar(command))
                .isInstanceOf(TransacaoInvalidaException.class);

        verifyNoInteractions(repository);
    }

}