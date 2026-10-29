package com.pablito.payflow.transaction.application;

import com.pablito.payflow.transaction.application.port.TransacaoRepository;
import com.pablito.payflow.transaction.domain.Transacao;

import java.util.UUID;

public class BuscarTransacaoService {

    private final TransacaoRepository repository;


    public BuscarTransacaoService(TransacaoRepository repository) {
        this.repository = repository;
    }

    public Transacao executar(UUID id) {
        return repository.buscarPorId(id).orElseThrow(
                () -> new TransacaoNaoEncontradaException("A Transação com o id "+id+" não foi encontrada."));
    }
}
