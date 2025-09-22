package com.stockechoes.api.portfolios;

import com.stockechoes.api.portfolios.exceptions.PortfolioExceptions.PortfolioNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PortfolioService {
    @Inject
    PortfolioRepository portfolioRepository;

    public Portfolio findOrThrow(Long id) {
        return portfolioRepository.findByIdOptional(id)
                .orElseThrow(() -> new PortfolioNotFoundException(id));
    }
}
