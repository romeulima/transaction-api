package br.com.picpay.services;

import br.com.picpay.dtos.feign.AuthResponseFeignDTO;
import br.com.picpay.feign.AuthorizationClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionAuthService {

    private final AuthorizationClient authorizationClient;

    public boolean makeAuthorization() {
        try {
            AuthResponseFeignDTO result = this.authorizationClient.authorization();
            return result.status().startsWith("success");
        } catch (FeignException e) {
            return false;
        }
    }
}
