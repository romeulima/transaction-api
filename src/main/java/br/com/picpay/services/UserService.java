package br.com.picpay.services;

import br.com.picpay.domain.user.User;
import br.com.picpay.domain.user.UserType;
import br.com.picpay.domain.user.exceptions.NoBalanceException;
import br.com.picpay.domain.user.exceptions.UserWithoutAuthorizationException;
import br.com.picpay.dtos.user.UserRequestDTO;
import br.com.picpay.dtos.user.UserResponseDTO;
import br.com.picpay.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void validateTransaction(User payer, BigDecimal value){
        if (payer.getUserType() == UserType.MERCHANT) {
            throw new UserWithoutAuthorizationException("Merchants cannot send money");
        }

        if (payer.getBalance().compareTo(value) < 0) {
            throw new NoBalanceException("No balance available");
        }
    }

    public User findUserById(Long id){
        return this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = new User(userRequestDTO);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        this.saveUser(user);

        return new UserResponseDTO(user.getId(), user.getFullName(), user.getEmail(), user.getBalance(), user.getUserType());
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = this.userRepository.findAll();

        return users.stream().map(user -> new UserResponseDTO(user.getId(), user.getFullName(), user.getEmail(), user.getBalance(), user.getUserType())).toList();
    }
}
