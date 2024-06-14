package br.com.picpay.infra.exception;

import br.com.picpay.domain.transaction.exceptions.EmailSenderFailureException;
import br.com.picpay.domain.transaction.exceptions.PicPayNotAllowedTransactionException;
import br.com.picpay.domain.user.exceptions.InvalidCredentialsException;
import br.com.picpay.domain.user.exceptions.NoBalanceException;
import br.com.picpay.domain.user.exceptions.UserWithoutAuthorizationException;
import br.com.picpay.dtos.geral.ErrorDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(PicPayNotAllowedTransactionException.class)
    public ResponseEntity<ErrorDTO> handlePicPayNotAllowedTransactionException(PicPayNotAllowedTransactionException e){
        return ResponseEntity.internalServerError().body(new ErrorDTO(e.getMessage(), "500"));
    }

    @ExceptionHandler(EmailSenderFailureException.class)
    public ResponseEntity<ErrorDTO> handleEmailSenderFailureException(EmailSenderFailureException e) {
        return ResponseEntity.internalServerError().body(new ErrorDTO(e.getMessage(), "500"));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO("User is already registered", "409"));
    }

    @ExceptionHandler(UserWithoutAuthorizationException.class)
    public ResponseEntity<ErrorDTO> handleUserWithoutAuthorizationException(UserWithoutAuthorizationException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDTO(e.getMessage(), "403"));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEntityNotFound(EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NoBalanceException.class)
    public ResponseEntity<ErrorDTO> handleNoBalanceException(NoBalanceException e) {
        return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage(), "400"));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorDTO> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDTO(e.getMessage(), "401"));
    }

}
