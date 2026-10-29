package com.pablito.payflow.transaction.infrastructure.web;

import com.pablito.payflow.transaction.domain.MeioPagamento;
import com.pablito.payflow.transaction.domain.StatusTransacao;
import com.pablito.payflow.transaction.domain.Transacao;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransacaoResponse(UUID id, String merchantId,
                                BigDecimal valor,
                                MeioPagamento meioPagamento,
                                StatusTransacao status,
                                Instant criadaEm) {

    public static TransacaoResponse from(Transacao transacao) {
        return new TransacaoResponse(
                transacao.getId(),
                transacao.getMerchantId(),
                transacao.getValor(),
                transacao.getMeioPagamento(),
                transacao.getStatus(),
                transacao.getCriadaEm());
    }
}
