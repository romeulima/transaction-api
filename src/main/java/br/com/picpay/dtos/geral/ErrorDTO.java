package br.com.picpay.dtos.geral;

public record ErrorDTO(
        String message,
        String statusCode
) {
}
