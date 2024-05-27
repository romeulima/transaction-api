package br.com.picpay.dtos.user;

import br.com.picpay.domain.user.UserType;

import java.math.BigDecimal;

public record UserRequestDTO(
        String fullName,
        String document,
        String email,
        String password,
        BigDecimal balance,
        UserType userType
) {
}
