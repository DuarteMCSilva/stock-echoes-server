package com.stockechoes.api.portfolios.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.io.FileNotFoundException;
import java.util.Map;

public class PortfolioExceptionMappers {

    @Provider
    public static class PortfolioNotFoundMapper implements ExceptionMapper<PortfolioExceptions.PortfolioNotFoundException> {

        @Override
        public Response toResponse(PortfolioExceptions.PortfolioNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of(
                            "error", e.getMessage(),
                            "portfolioId", e.getPortfolioId()
                            ))
                    .build();
        }
    }

    @Provider
    public static class FileNotFoundExceptionMapper implements ExceptionMapper<FileNotFoundException> {

        @Override
        public Response toResponse(FileNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of(
                            "error", e.getMessage(),
                            "code", "FileNotFound"
                    ))
                    .build();
        }
    }
}
