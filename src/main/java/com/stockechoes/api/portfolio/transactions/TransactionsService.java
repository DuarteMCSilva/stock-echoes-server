package com.stockechoes.api.portfolio.transactions;

import com.stockechoes.api.portfolio.portfolios.Portfolio;
import com.stockechoes.api.portfolio.portfolios.PortfolioService;
import com.stockechoes.api.portfolio.portfolios.exceptions.PortfolioExceptions.PortfolioNotFoundException;
import com.stockechoes.services.utility.csv.CsvReaderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.InputStream;
import java.util.List;

@ApplicationScoped
public class TransactionsService {

    @Inject
    TransactionsRepository transactionsRepository;

    @Inject
    PortfolioService portfolioService;

    @Inject
    CsvReaderService csvReaderService;

    public List<TransactionsDto> getPortfolioTransactions(
            Long portfolioId
    ) {
        var portfolio = portfolioService.findPortfolioOnAccountByIdOrThrow(portfolioId);

        return portfolio.getTransactions().stream()
                .map(TransactionsDto::new).toList();
    }

    @Transactional
    public List<Transaction> postTransactionHistory ( InputStream csvFile, Long portfolioId
        ) throws PortfolioNotFoundException {
        Portfolio portfolio = portfolioService.findPortfolioOnAccountByIdOrThrow(portfolioId);

        List<Transaction> importedTransactions = csvReaderService
                .getTransactionsFromCsv(csvFile, portfolio);

        System.out.println("$postTransactionHistory - Persisting transactions...");
        transactionsRepository.persist(importedTransactions);

        // TODO: Avoid duplicate values, on duplicate requests.
        return importedTransactions;
    }
}
