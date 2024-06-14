package br.com.picpay.controllers;

import br.com.picpay.domain.user.User;
import br.com.picpay.domain.user.exceptions.InvalidCredentialsException;
import br.com.picpay.dtos.user.AuthenticationRequestDTO;
import br.com.picpay.dtos.user.AuthenticationResponseDTO;
import br.com.picpay.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final AuthenticationService  authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody AuthenticationRequestDTO requestDTO) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(requestDTO.email(), requestDTO.password());
            Authentication auth = authenticationManager.authenticate(usernamePassword);

            String token = authService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new AuthenticationResponseDTO(token));
        } catch (Exception e) {
            throw new InvalidCredentialsException("Email or Password incorrect");
        }
    }
}
