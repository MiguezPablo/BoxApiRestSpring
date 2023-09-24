package com.example.boxCustodia.exceptions;

public class ErrorParametro extends RuntimeException {
    private ErrorPersonalizado errorPersonalizado;

    public ErrorParametro(String message, ErrorPersonalizado errorPersonalizado) {
        super(message);
        this.errorPersonalizado = errorPersonalizado;
    }

    public ErrorPersonalizado getErrorPersonalizado() {
        return errorPersonalizado;
    }
}
