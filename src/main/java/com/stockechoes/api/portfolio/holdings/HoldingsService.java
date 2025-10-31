package com.stockechoes.api.portfolio.holdings;

import com.stockechoes.api.portfolio.transactions.TransactionsDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class HoldingsService {

    @Inject
    TransactionsDao transactionsDao;

    public List<HoldingDto> getCurrentHoldings(String portfolioId) {
        if(portfolioId == null || portfolioId.isEmpty()) {
            throw new RuntimeException("No portfolio id was given");
        }

        return transactionsDao.getHoldings(portfolioId);
    }
}
