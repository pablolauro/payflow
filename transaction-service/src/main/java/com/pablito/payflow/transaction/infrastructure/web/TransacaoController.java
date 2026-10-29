package com.pablito.payflow.transaction.infrastructure.web;

import com.pablito.payflow.transaction.application.BuscarTransacaoService;
import com.pablito.payflow.transaction.application.CriarTransacaoService;
import com.pablito.payflow.transaction.domain.Transacao;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransacaoController {

    private final CriarTransacaoService criarTransacaoService;
    private final BuscarTransacaoService buscarTransacaoService;


    public TransacaoController(CriarTransacaoService criarTransacaoService, BuscarTransacaoService buscarTransacaoService) {
        this.criarTransacaoService = criarTransacaoService;
        this.buscarTransacaoService = buscarTransacaoService;
    }

    @PostMapping
    public ResponseEntity<TransacaoResponse> criar(@Valid @RequestBody CriarTransacaoRequest request){
        Transacao transacao = criarTransacaoService.executar(request.toCommand());

        URI location = URI.create("/transactions/"+transacao.getId());
        return ResponseEntity.created(location).body(TransacaoResponse.from(transacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoResponse> buscar(@PathVariable UUID id) {
        Transacao transacao = buscarTransacaoService.executar(id);

        return ResponseEntity.ok(TransacaoResponse.from(transacao));
    }
}
