package com.pablito.payflow.transaction.application;

import com.pablito.payflow.transaction.application.port.TransacaoRepository;
import com.pablito.payflow.transaction.domain.Transacao;

public class CriarTransacaoService {

    private final TransacaoRepository repository;

    public CriarTransacaoService(TransacaoRepository repository) {
        this.repository = repository;
    }

    public Transacao executar(CriarTransacaoCommand command) {
        Transacao transacao = Transacao.criar(
                command.merchantId(),
                command.valor(),
                command.meioPagamento()
        );

        repository.salvar(transacao);
        return transacao;
    }

}
