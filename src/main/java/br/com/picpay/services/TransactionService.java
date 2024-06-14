package br.com.picpay.services;

import br.com.picpay.domain.transaction.Transaction;
import br.com.picpay.domain.transaction.exceptions.PicPayNotAllowedTransactionException;
import br.com.picpay.domain.user.User;
import br.com.picpay.dtos.feign.EmailRequestDTO;
import br.com.picpay.dtos.transaction.TransactionRequestDTO;
import br.com.picpay.dtos.transaction.TransactionResponseDTO;
import br.com.picpay.dtos.user.UserResponseDTO;
import br.com.picpay.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserService userService;

    private final TransactionRepository transactionRepository;

    private final TransactionAuthService transactionAuthService;

    private final EmailService emailService;

    @Transactional
    public TransactionResponseDTO makeTransaction(TransactionRequestDTO transactionDTO){

        User payer = this.userService.findUserById(transactionDTO.payer());
        User payee = this.userService.findUserById(transactionDTO.payee());

        this.userService.validateTransaction(payer, transactionDTO.value());

        boolean allow = this.transactionAuthService.makeAuthorization();

        if (!allow){
            throw new PicPayNotAllowedTransactionException("PicPay don't allowed this transaction");
        }

        Transaction transaction = this.createTransaction(payer, payee, transactionDTO.value());
        updateBalances(payer, payee, transactionDTO.value());

        this.transactionRepository.save(transaction);

        this.emailService.sendEmail(new EmailRequestDTO(
                payer.getEmail(),
                "PicPay Transaction",
                this.emailService.generateMessage(payer.getFullName(), transactionDTO.value())));

        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getValue(),
                new UserResponseDTO(payer.getId(), payer.getFullName(), payer.getEmail(), payer.getBalance(), payer.getUserType()),
                new UserResponseDTO(payee.getId(), payee.getFullName(), payee.getEmail(), payee.getBalance(), payee.getUserType()),
                transaction.getTimestamp());
    }

    public Transaction createTransaction(User payer, User payee, BigDecimal value) {
        return Transaction.builder().value(value).payer(payer).payee(payee).build();
    }

    public void updateBalances(User payer, User payee, BigDecimal value) {
        payer.setBalance(payer.getBalance().subtract(value));
        payee.setBalance(payee.getBalance().add(value));
    }
}
