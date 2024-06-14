package br.com.picpay.dtos.transaction;

import br.com.picpay.dtos.user.UserResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
        Long id,
        BigDecimal value,
        UserResponseDTO payer,
        UserResponseDTO payee,
        LocalDateTime timestamp
) {
}
