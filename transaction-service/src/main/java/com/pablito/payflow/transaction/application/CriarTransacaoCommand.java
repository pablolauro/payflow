package com.pablito.payflow.transaction.application;

import com.pablito.payflow.transaction.domain.MeioPagamento;

import java.math.BigDecimal;

public record CriarTransacaoCommand(String merchantId, BigDecimal valor, MeioPagamento meioPagamento) {
}
