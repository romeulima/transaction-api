package br.com.picpay.controllers;

import br.com.picpay.domain.user.User;
import br.com.picpay.dtos.user.UserRequestDTO;
import br.com.picpay.dtos.user.UserResponseDTO;
import br.com.picpay.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequest) {
        UserResponseDTO user = this.userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = this.userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
