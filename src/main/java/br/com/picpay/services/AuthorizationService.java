package br.com.picpay.services;

import br.com.picpay.dtos.feign.AuthResponseFeignDTO;
import br.com.picpay.feign.AuthorizationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    @Autowired
    private final AuthorizationClient authorizationClient;

    public boolean makeAuthorization() {
        AuthResponseFeignDTO result = this.authorizationClient.authorization();

        return result.status().startsWith("success");
    }
}
