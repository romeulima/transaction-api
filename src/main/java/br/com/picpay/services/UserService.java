package br.com.picpay.services;

import br.com.picpay.domain.user.User;
import br.com.picpay.domain.user.UserType;
import br.com.picpay.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public void validateTransaction(User payer, BigDecimal value) throws Exception {
        if (payer.getUserType() == UserType.COMMON) {
            throw new Exception("Lojistas nao podem enviar dinheiro");
        }

        if (payer.getBalance().compareTo(value) < 0) {
            throw new Exception("Saldo  insuficiente");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.userRepository.findById(id).orElseThrow(() -> new Exception("Usuario nao encontrado"));
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }
}
