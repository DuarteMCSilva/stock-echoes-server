package com.stockechoes.api.portfolios;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PortfolioService {
    @Inject
    PortfolioRepository portfolioRepository;

    public Portfolio ensurePortfolioPresence(Long portfolioId) {
        return portfolioRepository.findByIdOptional(portfolioId)
                .orElseGet( () -> {
                    Portfolio p = new Portfolio("Portfolio");
                    portfolioRepository.persist(p);
                    return p;
                });
    }
}
