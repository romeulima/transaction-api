package br.com.picpay.feign;

import br.com.picpay.dtos.feign.AuthResponseFeignDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "auth", url = "https://util.devi.tools/api/v2/authorize")
public interface AuthorizationClient {

    @GetMapping
    AuthResponseFeignDTO authorization();
}
