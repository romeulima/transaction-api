package br.com.picpay.domain.user.exceptions;

public class NoBalanceException extends RuntimeException{

    public NoBalanceException(String message){
        super(message);
    }
}
