package br.com.picpay.dtos.user;

import br.com.picpay.domain.user.UserType;

import java.math.BigDecimal;

public record UserResponseDTO(
        Long id,
        String fullName,
        String email,
        BigDecimal balance,
        UserType userType
) {
}
