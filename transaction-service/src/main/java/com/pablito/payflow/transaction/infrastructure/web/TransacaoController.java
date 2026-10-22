package com.pablito.payflow.transaction.infrastructure.web;

import com.pablito.payflow.transaction.application.CriarTransacaoService;
import com.pablito.payflow.transaction.domain.Transacao;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/transactions")
public class TransacaoController {

    private final CriarTransacaoService service;


    public TransacaoController(CriarTransacaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransacaoResponse> criar(@Valid @RequestBody CriarTransacaoRequest request){
        Transacao transacao = service.executar(request.toCommand());

        URI location = URI.create("/transactions/"+transacao.getId());
        return ResponseEntity.created(location).body(TransacaoResponse.from(transacao));
    }
}
