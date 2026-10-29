package com.pablito.payflow.transaction.application.port;

import com.pablito.payflow.transaction.domain.Transacao;

import java.util.Optional;
import java.util.UUID;

public interface TransacaoRepository {
   void salvar(Transacao transacao);
   Optional<Transacao> buscarPorId(UUID id);
}
