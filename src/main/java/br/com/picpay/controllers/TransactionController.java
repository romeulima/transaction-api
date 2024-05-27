package br.com.picpay.controllers;

import br.com.picpay.domain.transaction.Transaction;
import br.com.picpay.dtos.transaction.TransactionRequestDTO;
import br.com.picpay.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    @Autowired
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRequestDTO requestDTO) throws Exception {
        Transaction transaction = this.transactionService.makeTransaction(requestDTO);
        return ResponseEntity.ok(transaction);
    }
}
