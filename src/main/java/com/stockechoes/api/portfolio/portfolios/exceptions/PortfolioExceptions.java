package com.stockechoes.api.portfolio.portfolios.exceptions;

import com.stockechoes.api.GenericApiException;
import jakarta.ws.rs.core.Response;
import lombok.Getter;

public class PortfolioExceptions {
    @Getter
    public static class PortfolioNotFoundException extends GenericApiException {
        private final Long portfolioId;

        public PortfolioNotFoundException(Long portfolioId) {
            super(Response.Status.NOT_FOUND, "Portfolio not found in this account with id: " + portfolioId);
            this.portfolioId = portfolioId;
        }
    }

}
