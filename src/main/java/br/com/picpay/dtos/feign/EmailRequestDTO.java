package br.com.picpay.dtos.feign;

public record EmailRequestDTO(
        String to,
        String subject,
        String body
) {
}
