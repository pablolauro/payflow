package com.pablito.payflow.transaction.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class TransacaoTest {

    @Test
    void deveCriarTransacaoComStatusRecebida(){
        Transacao transacao = Transacao.criar("loja-123",new BigDecimal("19.99"),MeioPagamento.CREDITO);

        assertNotNull(transacao.getId());
        assertEquals(StatusTransacao.RECEBIDA, transacao.getStatus());
        assertNotNull(transacao.getCriadaEm());
    }

    @Test
    void naoDeveCriarTransacaoComValorZero() {
        assertThatThrownBy(() -> Transacao.criar("loja-123", BigDecimal.ZERO, MeioPagamento.PIX))
                .isInstanceOf(TransacaoInvalidaException.class)
                .hasMessage("O valor da transação deve ser maior que zero.");
    }

    @Test
    void naoDeveCriarMerchantIdEmBranco() {
        assertThatThrownBy(() -> Transacao.criar("",new BigDecimal("19.99"), MeioPagamento.PIX))
                .isInstanceOf(TransacaoInvalidaException.class)
                .hasMessage("O lojista é obrigatório.");
    }

    @Test
    void naoDeveCriarMeioDePagamentoNulo() {
        assertThatThrownBy(() -> Transacao.criar("loja-123", new BigDecimal("19.99"), null))
                .isInstanceOf(TransacaoInvalidaException.class)
                .hasMessage("O meio de pagamento é obrigatório.");
    }

    @Test
    void transacaoViraAutorizada() {
        Transacao transacao = Transacao.criar("loja-123",new BigDecimal("19.99"),MeioPagamento.CREDITO);
        transacao.autorizar();

        assertEquals(StatusTransacao.AUTORIZADA,transacao.getStatus());
    }

    @Test
    void naoNegarDepoisDeAutorizada() {
        Transacao transacao = Transacao.criar("loja-123",new BigDecimal("19.99"),MeioPagamento.CREDITO);
        transacao.autorizar();

        assertThatThrownBy(transacao::negar)
                .isInstanceOf(TransacaoInvalidaException.class)
                .hasMessage("Só é possível alterar uma transação com status RECEBIDA.");

    }


}