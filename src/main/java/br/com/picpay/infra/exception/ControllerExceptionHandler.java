package br.com.picpay.infra.exception;

import br.com.picpay.domain.transaction.exceptions.PicPayServerErrorException;
import br.com.picpay.domain.user.exceptions.UserWithoutAuthorizationException;
import br.com.picpay.dtos.geral.ErrorDTO;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorDTO> handleFeignException(FeignException e){
        return ResponseEntity.internalServerError().body(new ErrorDTO("Erro com o serviço de autorização", "500"));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        return ResponseEntity.badRequest().body(new ErrorDTO("Usuario ja cadastrado", "400"));
    }

    @ExceptionHandler(PicPayServerErrorException.class)
    public ResponseEntity<ErrorDTO> handleException(PicPayServerErrorException e){
        return ResponseEntity.internalServerError().body(new ErrorDTO(e.getMessage(), "500"));
    }

    @ExceptionHandler(UserWithoutAuthorizationException.class)
    public ResponseEntity<ErrorDTO> handleUserWithoutAuthorizationException(UserWithoutAuthorizationException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDTO(e.getMessage(), "403"));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEntityNotFound(EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

}
