package com.stockechoes.api.portfolio.portfolios;

import com.stockechoes.api.accounts.Account;
import com.stockechoes.api.accounts.AccountService;
import com.stockechoes.api.portfolio.holdings.HoldingDto;
import com.stockechoes.api.portfolio.transactions.TransactionsDto;
import com.stockechoes.api.portfolio.holdings.HoldingService;
import com.stockechoes.api.portfolio.transactions.TransactionsService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.graphql.*;

import java.util.List;

@GraphQLApi
@SuppressWarnings("unused")
public class PortfolioQueries {

    // https://piotrminkowski.com/2021/04/14/advanced-graphql-with-quarkus/

    @Inject
    TransactionsService transactionsService;

    @Inject
    HoldingService holdingService;

    @Inject
    PortfolioRepository portfolioRepository;

    @Inject
    PortfolioService portfolioService;

    @Inject
    AccountService accountService;

    @Query("account")
    public Account getAccount() {
        return accountService.getAccountFromContextOrThrow();
    }

    @Query("portfolios")
    public List<Portfolio> getPortfolios() {
        return portfolioService.getAccountPortfolios();
    }

    @Query("portfolio")
    public Portfolio getPortfolio(@Name("id") Long id) {
        return portfolioRepository.findById(id);
    }

    @Query("transactions")
    public List<TransactionsDto> getTransactions(@Source Portfolio portfolio) {
        return transactionsService.getPortfolioTransactions(portfolio.getId());
    }

    @Query("holdings")
    public List<HoldingDto> getHoldings(@Source Portfolio portfolio) {
        return holdingService.getCurrentHoldings(portfolio.getId());
    }

    @Mutation
    @Transactional
    public Long createPortfolio(Portfolio portfolio){
        portfolioRepository.persist(portfolio);
        return portfolio.getId();
    }
}
