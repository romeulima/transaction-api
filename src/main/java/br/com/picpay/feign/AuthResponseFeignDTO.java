package br.com.picpay.feign;

public record AuthResponseFeignDTO(
        String status,
        DataFeignDTO data
) {
}
