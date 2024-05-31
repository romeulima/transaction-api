package br.com.picpay.domain.transaction.exceptions;

public class PicPayServerErrorException extends RuntimeException {

    public PicPayServerErrorException(String message) {
        super(message);
    }
}
