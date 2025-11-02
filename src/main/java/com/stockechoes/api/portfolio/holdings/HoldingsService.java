package com.stockechoes.api.portfolio.holdings;

import com.stockechoes.api.portfolio.portfolios.Portfolio;
import com.stockechoes.api.portfolio.portfolios.PortfolioService;
import com.stockechoes.api.portfolio.portfolios.exceptions.PortfolioExceptions.PortfolioNotFoundException;
import com.stockechoes.api.portfolio.transactions.TransactionsDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class HoldingsService {

    @Inject
    TransactionsDao transactionsDao;

    @Inject
    PortfolioService portfolioService;

    public List<HoldingDto> getCurrentHoldings(Long portfolioId) throws PortfolioNotFoundException {

        // Make sure the portfolio belongs to the authenticated account.
        Portfolio accountPortfolioById = portfolioService.findPortfolioOnAccountByIdOrThrow(portfolioId);

        return transactionsDao.getHoldings(accountPortfolioById.getId());
    }
}
