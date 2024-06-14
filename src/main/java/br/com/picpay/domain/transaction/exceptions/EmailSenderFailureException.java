package br.com.picpay.domain.transaction.exceptions;

public class EmailSenderFailureException extends RuntimeException {

    public EmailSenderFailureException (String message) {
        super(message);
    }
}
