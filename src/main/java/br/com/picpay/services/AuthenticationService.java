package br.com.picpay.services;

import br.com.picpay.domain.user.User;
import br.com.picpay.infra.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TokenService tokenService;

    public String generateToken(User user) {
        return tokenService.generateToken(user);
    }
}
