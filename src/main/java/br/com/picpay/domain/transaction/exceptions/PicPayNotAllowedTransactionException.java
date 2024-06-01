package br.com.picpay.domain.transaction.exceptions;

public class PicPayNotAllowedTransactionException extends RuntimeException {

    public PicPayNotAllowedTransactionException(String message) {
        super(message);
    }
}
