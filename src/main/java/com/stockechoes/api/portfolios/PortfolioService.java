package com.stockechoes.api.portfolios;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class PortfolioService {
    @Inject
    PortfolioRepository portfolioRepository;

    public Optional<Portfolio> getPortfolioById(Long id) {
        return portfolioRepository.findByIdOptional(id);
    }
}
