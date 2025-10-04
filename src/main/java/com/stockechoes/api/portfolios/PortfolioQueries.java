package com.stockechoes.api.portfolios;

import com.stockechoes.api.holdings.HoldingDto;
import com.stockechoes.api.transactions.TransactionsDto;
import com.stockechoes.api.holdings.HoldingsService;
import com.stockechoes.api.transactions.TransactionsService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.graphql.*;

import java.util.List;

@GraphQLApi()
@SuppressWarnings("unused")
public class PortfolioQueries {

    // https://piotrminkowski.com/2021/04/14/advanced-graphql-with-quarkus/

    @Inject
    TransactionsService transactionsService;

    @Inject
    HoldingsService holdingsService;

    @Inject
    PortfolioRepository portfolioRepository;

    @Query("portfolio")
    public Portfolio getPortfolio(@Name("id") Long id) {
        return portfolioRepository.findById(id);
    }

    @Query("transactions")
    public List<TransactionsDto> getTransactions(@Source Portfolio portfolio) {
        return transactionsService.getPortfolioTransactions(portfolio.id, null);
    }

    @Query("holdings")
    public List<HoldingDto> getHoldings(@Source Portfolio portfolio) {
        return holdingsService.getCurrentHoldings(String.valueOf(portfolio.id));
    }

    @Mutation
    @Transactional
    public Long createPortfolio(Portfolio portfolio){
        return portfolioRepository.createPortfolio(portfolio.getName());
    }
}
