package br.com.picpay.services;

import br.com.picpay.domain.transaction.Transaction;
import br.com.picpay.domain.transaction.exceptions.PicPayServerErrorException;
import br.com.picpay.domain.user.User;
import br.com.picpay.dtos.feign.EmailRequestDTO;
import br.com.picpay.dtos.transaction.TransactionRequestDTO;
import br.com.picpay.dtos.feign.AuthResponseFeignDTO;
import br.com.picpay.feign.AuthorizationClient;
import br.com.picpay.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {

    @Autowired
    private final UserService userService;

    @Autowired
    private final TransactionRepository transactionRepository;

    @Autowired
    private final AuthorizationClient authorizationClient;

    @Autowired
    private final EmailService emailService;

    @Transactional
    public Transaction makeTransaction(TransactionRequestDTO transactionDTO){

        User payer = this.userService.findUserById(transactionDTO.payer());
        User payee = this.userService.findUserById(transactionDTO.payee());

        validateTransaction(payer, transactionDTO.value());

        authorizeTransaction();

        Transaction transaction = this.createTransaction(payer, payee, transactionDTO.value());
        updateBalances(payer, payee, transactionDTO.value());

        this.transactionRepository.save(transaction);

        this.emailService.sendEmail(new EmailRequestDTO(
                payer.getEmail(),
                "PicPay Transaction",
                this.emailService.generateMessage(payer.getFullName(), transactionDTO.value())));

        return transaction;
    }

    private void validateTransaction(User payer, BigDecimal value) {
        userService.validateTransaction(payer, value);
    }

    private boolean makeAuthorization() {
        AuthResponseFeignDTO authResponse = this.authorizationClient.authorization();

        return authResponse.status().startsWith("success");
    }

    private void authorizeTransaction(){
        if (!makeAuthorization()) {
            throw new PicPayServerErrorException("Erro de autorização! Nenhuma transação concluída");
        }
    }

    private Transaction createTransaction(User payer, User payee, BigDecimal value) {
        return Transaction.builder().value(value).payer(payer).payee(payee).build();
    }

    private void updateBalances(User payer, User payee, BigDecimal value) {
        payer.setBalance(payer.getBalance().subtract(value));
        payee.setBalance(payee.getBalance().add(value));
    }
}
