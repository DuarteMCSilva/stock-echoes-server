package com.stockechoes.api.portfolios.exceptions;

import lombok.Getter;

public class PortfolioExceptions {
    @Getter
    public static class PortfolioNotFoundException extends RuntimeException {
        private final Long portfolioId;

        public PortfolioNotFoundException(Long portfolioId) {
            super("Portfolio not found.");
            this.portfolioId = portfolioId;
        }
    }

}
