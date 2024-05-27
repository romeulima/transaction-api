package br.com.picpay.services;

import br.com.picpay.domain.user.User;
import br.com.picpay.domain.user.UserType;
import br.com.picpay.domain.user.exceptions.NoBalanceException;
import br.com.picpay.domain.user.exceptions.UserWithoutAuthorizationException;
import br.com.picpay.dtos.user.UserRequestDTO;
import br.com.picpay.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public void validateTransaction(User payer, BigDecimal value){
        if (payer.getUserType() == UserType.MERCHANT) {
            throw new UserWithoutAuthorizationException("Lojistas nao podem enviar dinheiro");
        }

        if (payer.getBalance().compareTo(value) < 0) {
            throw new NoBalanceException("Saldo  insuficiente");
        }
    }

    public User findUserById(Long id){
        return this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));
    }

    public User createUser(UserRequestDTO userRequestDTO) {
        User user = new User(userRequestDTO);
        this.saveUser(user);
        return user;
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
}
