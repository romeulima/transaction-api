package br.com.picpay.services;

import br.com.picpay.domain.transaction.Transaction;
import br.com.picpay.domain.user.User;
import br.com.picpay.dtos.transaction.TransactionRequestDTO;
import br.com.picpay.feign.AuthResponseFeignDTO;
import br.com.picpay.feign.AuthorizationClient;
import br.com.picpay.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    @Autowired
    private final UserService userService;

    @Autowired
    private final TransactionRepository transactionRepository;

    @Autowired
    private final AuthorizationClient authorizationClient;

    @Transactional
    public Transaction makeTransaction(TransactionRequestDTO transactionDTO) throws Exception {

        User payer = this.userService.findUserById(transactionDTO.payer());
        User payee = this.userService.findUserById(transactionDTO.payee());

        this.userService.validateTransaction(payer, transactionDTO.value());

        if (!this.makeAuthorization()) {
            throw new Exception("Erro de autorização! Nenhuma transação concluída");
        }

        Transaction transaction = Transaction.builder().value(transactionDTO.value()).payer(payer).payee(payee).build();
        payer.setBalance(payer.getBalance().subtract(transactionDTO.value()));
        payee.setBalance(payee.getBalance().add(transactionDTO.value()));

        return this.transactionRepository.save(transaction);
    }


    private boolean makeAuthorization() {
        AuthResponseFeignDTO authResponse = this.authorizationClient.authorization();

        return authResponse.status().startsWith("success");
    }
}
