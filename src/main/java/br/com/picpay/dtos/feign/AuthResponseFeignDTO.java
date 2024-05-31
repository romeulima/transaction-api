package br.com.picpay.dtos.feign;

public record AuthResponseFeignDTO(
        String status,
        DataFeignDTO data
) {
}
