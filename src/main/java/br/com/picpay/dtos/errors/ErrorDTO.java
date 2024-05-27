package br.com.picpay.dtos.errors;

public record ErrorDTO(
        String message,
        String statusCode
) {
}
