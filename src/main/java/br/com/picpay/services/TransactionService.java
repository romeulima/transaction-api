package br.com.picpay.services;

import br.com.picpay.domain.transaction.Transaction;
import br.com.picpay.domain.transaction.exceptions.PicPayNotAllowedTransactionException;
import br.com.picpay.domain.user.User;
import br.com.picpay.dtos.feign.EmailRequestDTO;
import br.com.picpay.dtos.transaction.TransactionRequestDTO;
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
    private final AuthorizationService authorizationService;

    @Autowired
    private final EmailService emailService;

    @Transactional
    public Transaction makeTransaction(TransactionRequestDTO transactionDTO){

        User payer = this.userService.findUserById(transactionDTO.payer());
        User payee = this.userService.findUserById(transactionDTO.payee());

        this.userService.validateTransaction(payer, transactionDTO.value());

        if (!(this.authorizationService.makeAuthorization())){
            throw new PicPayNotAllowedTransactionException("PicPay don't allowed this transaction");
        }

        Transaction transaction = this.createTransaction(payer, payee, transactionDTO.value());
        updateBalances(payer, payee, transactionDTO.value());

        this.transactionRepository.save(transaction);

        this.emailService.sendEmail(new EmailRequestDTO(
                payer.getEmail(),
                "PicPay Transaction",
                this.emailService.generateMessage(payer.getFullName(), transactionDTO.value())));

        return transaction;
    }

    public Transaction createTransaction(User payer, User payee, BigDecimal value) {
        return Transaction.builder().value(value).payer(payer).payee(payee).build();
    }

    public void updateBalances(User payer, User payee, BigDecimal value) {
        payer.setBalance(payer.getBalance().subtract(value));
        payee.setBalance(payee.getBalance().add(value));
    }
}
