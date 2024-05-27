package br.com.picpay.domain.user.exceptions;

public class UserWithoutAuthorizationException extends RuntimeException{

    public UserWithoutAuthorizationException(String message){
        super(message);
    }
}
