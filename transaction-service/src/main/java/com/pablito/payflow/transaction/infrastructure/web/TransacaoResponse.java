package com.pablito.payflow.transaction.infrastructure.web;

import com.pablito.payflow.transaction.domain.StatusTransacao;
import com.pablito.payflow.transaction.domain.Transacao;

import java.time.Instant;
import java.util.UUID;

public record TransacaoResponse(UUID id, StatusTransacao status, Instant criadaEm) {

    public static TransacaoResponse from(Transacao transacao) {
        return new TransacaoResponse(transacao.getId(),transacao.getStatus(),transacao.getCriadaEm());
    }
}
