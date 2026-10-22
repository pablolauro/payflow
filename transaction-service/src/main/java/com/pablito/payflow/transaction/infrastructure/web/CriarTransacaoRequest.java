package com.pablito.payflow.transaction.infrastructure.web;


import com.pablito.payflow.transaction.application.CriarTransacaoCommand;
import com.pablito.payflow.transaction.domain.MeioPagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CriarTransacaoRequest(@NotBlank String merchantId, @NotNull @Positive BigDecimal valor, @NotNull MeioPagamento meioPagamento) {

    public CriarTransacaoCommand toCommand() {
        return new CriarTransacaoCommand(merchantId,valor,meioPagamento);
    }
}
