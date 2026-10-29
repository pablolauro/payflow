package com.pablito.payflow.transaction.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransacaoTest {

    @Test
    void deveCriarTransacaoComStatusRecebida(){
        Transacao transacao = Transacao.criar("loja-123",new BigDecimal("19.99"),MeioPagamento.CREDITO);

        assertThat(transacao.getId()).isNotNull();
        assertThat(transacao.getCriadaEm()).isNotNull();
        assertThat(transacao.getStatus()).isEqualTo(StatusTransacao.RECEBIDA);
    }

    @Test
    void naoDeveCriarTransacaoComValorZero() {
        assertThatThrownBy(() -> Transacao.criar("loja-123", BigDecimal.ZERO, MeioPagamento.PIX))
                .isInstanceOf(TransacaoInvalidaException.class)
                .hasMessage("O valor da transação deve ser maior que zero.");
    }

    @Test
    void naoDeveCriarTransacaoComMerchantIdEmBranco() {
        assertThatThrownBy(() -> Transacao.criar("",new BigDecimal("19.99"), MeioPagamento.PIX))
                .isInstanceOf(TransacaoInvalidaException.class)
                .hasMessage("O lojista é obrigatório.");
    }

    @Test
    void naoDeveCriarTransacaoComMeioDePagamentoNulo() {
        assertThatThrownBy(() -> Transacao.criar("loja-123", new BigDecimal("19.99"), null))
                .isInstanceOf(TransacaoInvalidaException.class)
                .hasMessage("O meio de pagamento é obrigatório.");
    }

    @Test
    void deveAutorizarTransacaoRecebida() {
        Transacao transacao = Transacao.criar("loja-123",new BigDecimal("19.99"),MeioPagamento.CREDITO);
        transacao.autorizar();

        assertThat(transacao.getStatus()).isEqualTo(StatusTransacao.AUTORIZADA);
    }

    @Test
    void naoDeveNegarTransacaoAutorizada() {
        Transacao transacao = Transacao.criar("loja-123",new BigDecimal("19.99"),MeioPagamento.CREDITO);
        transacao.autorizar();

        assertThatThrownBy(transacao::negar)
                .isInstanceOf(TransacaoInvalidaException.class)
                .hasMessage("Só é possível alterar uma transação com status RECEBIDA.");
    }
}