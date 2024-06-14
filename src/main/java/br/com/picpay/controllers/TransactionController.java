package br.com.picpay.controllers;

import br.com.picpay.dtos.transaction.TransactionRequestDTO;
import br.com.picpay.dtos.transaction.TransactionResponseDTO;
import br.com.picpay.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionRequestDTO requestDTO){
        TransactionResponseDTO transaction = this.transactionService.makeTransaction(requestDTO);
        return ResponseEntity.ok(transaction);
    }
}
