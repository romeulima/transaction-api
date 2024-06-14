package br.com.picpay.dtos.user;

public record AuthenticationRequestDTO(
        String email,
        String password
) {
}
