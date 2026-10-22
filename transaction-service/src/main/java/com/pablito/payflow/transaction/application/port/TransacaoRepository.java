package com.pablito.payflow.transaction.application.port;

import com.pablito.payflow.transaction.domain.Transacao;

public interface TransacaoRepository {
   void salvar(Transacao transacao);
}
