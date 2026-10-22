package com.pablito.payflow.transaction.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Transacao {

    private final UUID id;
    private final String merchantId;
    private final BigDecimal valor;
    private final MeioPagamento meioPagamento;
    private final Instant criadaEm;
    private StatusTransacao status;

    private Transacao(UUID id, String merchantId, BigDecimal valor, MeioPagamento meioPagamento, StatusTransacao status, Instant criadaEm) {
        this.id = id;
        this.merchantId = merchantId;
        this.valor = valor;
        this.meioPagamento = meioPagamento;
        this.status = status;
        this.criadaEm = criadaEm;
    }

    public static Transacao criar(String merchantId, BigDecimal valor, MeioPagamento meioPagamento ) {
        if (merchantId == null || merchantId.isBlank()) {
            throw new TransacaoInvalidaException("O lojista é obrigatório.");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransacaoInvalidaException("O valor da transação deve ser maior que zero.");
        }
        if (meioPagamento == null) {
            throw new TransacaoInvalidaException("O meio de pagamento é obrigatório.");
        }
        return new Transacao(UUID.randomUUID(), merchantId, valor, meioPagamento,
                StatusTransacao.RECEBIDA, Instant.now());
    }

    // Usado pelo adapter para remontar a partir do banco
    public static Transacao reconstituir(UUID id, String merchantId, BigDecimal valor,
                                         MeioPagamento meioPagamento, StatusTransacao status, Instant criadaEm) {
        return new Transacao(id, merchantId, valor, meioPagamento, status, criadaEm);
    }

    public void autorizar() {
        validarQueEstaRecebida();
        this.status = StatusTransacao.AUTORIZADA;
    }

    public void negar() {
        validarQueEstaRecebida();
        this.status = StatusTransacao.NEGADA;
    }

    private void validarQueEstaRecebida() {
        if (status != StatusTransacao.RECEBIDA) {
            throw new TransacaoInvalidaException("Só é possível alterar uma transação com status RECEBIDA.");
        }
    }

}
