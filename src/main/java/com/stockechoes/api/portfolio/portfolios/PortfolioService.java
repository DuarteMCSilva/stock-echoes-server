package com.stockechoes.api.portfolio.portfolios;

import com.stockechoes.api.GenericApiException;
import com.stockechoes.api.portfolio.portfolios.exceptions.PortfolioExceptions.PortfolioNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class PortfolioService {
    @Inject
    PortfolioRepository portfolioRepository;

    public List<Portfolio> getPortfoliosByAccount(Long accountId) {
        return portfolioRepository.find("accountId", accountId).stream().toList();
    }

    public Portfolio findOrThrow(Long id) {
        return portfolioRepository.findByIdOptional(id)
                .orElseThrow(() -> new PortfolioNotFoundException(id));
    }

    public void savePortfolioWithoutDuplicates(Portfolio portfolio) {
        assertDuplicatePortfolio(portfolio);

        portfolioRepository.persist(portfolio);
    }


    private void assertDuplicatePortfolio(Portfolio portfolio) {
        if(portfolioRepository.findDuplicateOptional(portfolio.getName(), portfolio.getAccountId()).isPresent()) {
            throw new GenericApiException(Response.Status.CONFLICT, "Duplicate portfolio, please choose another name");
        }
    }
}
