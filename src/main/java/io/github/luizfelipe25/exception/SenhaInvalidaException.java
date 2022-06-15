package io.github.luizfelipe25.exception;

public class SenhaInvalidaException extends RuntimeException {

    public SenhaInvalidaException() {
        super("Senha Invalida");
    }
}
