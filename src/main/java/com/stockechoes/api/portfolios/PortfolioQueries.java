package com.stockechoes.api.portfolios;

import com.stockechoes.api.transactions.TransactionsDao;
import com.stockechoes.api.holdings.HoldingDto;
import com.stockechoes.api.transactions.TransactionsDto;
import com.stockechoes.api.holdings.HoldingsService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.graphql.*;

import java.util.List;

@GraphQLApi()
public class PortfolioQueries {

    // https://piotrminkowski.com/2021/04/14/advanced-graphql-with-quarkus/

    @Inject
    TransactionsDao transactionsDao;

    @Inject
    HoldingsService holdingsService;

    @Inject
    PortfolioRepository portfolioRepository;

    @Query("portfolio")
    public Portfolio getPortfolio(@Name("id") String id) {
        return portfolioRepository.getPortfolioById(id);
    }

    @Query("transactions")
    public List<TransactionsDto> getTransactions(@Source Portfolio portfolio) {
        return transactionsDao.getPortfolioTransactions(String.valueOf(portfolio.id));
    }

    @Query("holdings")
    public List<HoldingDto> getHoldings(@Source Portfolio portfolio) {
        return holdingsService.getCurrentHoldings(String.valueOf(portfolio.id));
    }

    @Mutation
    @Transactional
    public Portfolio createPortfolio(Portfolio portfolio){
        return portfolioRepository.createPortfolio(portfolio);
    }
}
