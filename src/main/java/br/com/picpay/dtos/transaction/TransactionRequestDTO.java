package br.com.picpay.dtos.transaction;

import java.math.BigDecimal;

public record TransactionRequestDTO(
        BigDecimal value,
        Long payer,
        Long payee
) {
}
