package com.stockechoes.api;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

@Provider
public class GenericApiExceptionMapper implements ExceptionMapper<GenericApiException> {

    @Override
    public Response toResponse(GenericApiException e) {
        return Response.status(e.getStatus())
                .entity(Map.of("error", e.getMessage()))
                .build();
    }
}
