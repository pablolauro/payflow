package com.pablito.payflow.transaction.infrastructure.persistence;

import com.pablito.payflow.transaction.application.port.TransacaoRepository;
import com.pablito.payflow.transaction.domain.Transacao;
import org.springframework.stereotype.Repository;

@Repository
public class TransacaoRepositoryAdapter implements TransacaoRepository {

    private final TransacaoJpaRepository repository;

    public TransacaoRepositoryAdapter(TransacaoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void salvar(Transacao transacao) {
        repository.save(toEntity(transacao));
    }

    public TransacaoEntity toEntity(Transacao transacao) {
        return new TransacaoEntity(transacao.getId(),
                transacao.getMerchantId(),
                transacao.getValor(),
                transacao.getMeioPagamento(),
                transacao.getStatus(),
                transacao.getCriadaEm());
    }

    public Transacao toDomain(TransacaoEntity entity) {
        return Transacao.reconstituir(
                entity.getId(),
                entity.getMerchantId(),
                entity.getValor(),
                entity.getMeioPagamento(),
                entity.getStatus(),
                entity.getCriadaEm());
    }
}
