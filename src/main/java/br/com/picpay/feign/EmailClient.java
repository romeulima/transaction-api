package br.com.picpay.feign;

import br.com.picpay.dtos.feign.EmailRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ses-aws", url = "http://localhost:8090/api/email")
public interface EmailClient {

    @PostMapping("/send")
    void sendEmail(@RequestBody EmailRequestDTO requestDTO);
}
