package com.stockechoes.api;

import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public class GenericApiException extends RuntimeException {
    private final Response.Status status;

    public GenericApiException(Response.Status status, String message) {
        super(message);
        this.status = status;
    }
}
