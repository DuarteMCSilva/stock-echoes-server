package com.stockechoes.rest.openfigi.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;

@Getter
public class FigiErrorException extends RuntimeException {

    private final String detail;
    private final String message;

    public FigiErrorException(JsonProcessingException e, String detail) {
        super("Figi Error: ");
        this.message = e.getMessage();
        this.detail = detail;
    }
}
