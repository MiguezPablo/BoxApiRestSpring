package com.example.boxCustodia.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorPersonalizado  {
    private long timestamp;
    private int status;
    private String message;
    private String path;

    public ErrorPersonalizado() {
    }
    public ErrorPersonalizado(int status, String message, String path) {
        this.timestamp = System.currentTimeMillis();
        this.status = status;
        this.message = message;
        this.path = path;
    }

}
