package com.pablito.payflow.transaction.application;

import com.pablito.payflow.transaction.application.port.TransacaoRepository;
import com.pablito.payflow.transaction.domain.MeioPagamento;
import com.pablito.payflow.transaction.domain.Transacao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarTransacaoServiceTest {

    @Mock
    private TransacaoRepository repository;

    @InjectMocks
    private BuscarTransacaoService service;

    @Test
    void deveRetornarTransacao() {
        Transacao transacao = Transacao.criar("loja-123", BigDecimal.valueOf(10L), MeioPagamento.CREDITO);

        when(repository.buscarPorId(transacao.getId())).thenReturn(Optional.of(transacao));

        Transacao resultado = service.executar(transacao.getId());

        assertThat(resultado).isEqualTo(transacao);

        verify(repository).buscarPorId(transacao.getId());
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarTransacao() {

        UUID id = UUID.randomUUID();

        when(repository.buscarPorId(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.executar(id))
                .isInstanceOf(TransacaoNaoEncontradaException.class)
                .hasMessage("A Transação com o id "+id+" não foi encontrada.");

        verify(repository).buscarPorId(id);
    }

}