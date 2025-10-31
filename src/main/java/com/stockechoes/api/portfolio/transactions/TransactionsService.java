package com.stockechoes.api.portfolio.transactions;

import com.stockechoes.api.portfolio.portfolios.PortfolioService;
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
            Long portfolioId, String ticker
    ) {
        List<Transaction> transactions;
        portfolioService.findOrThrow(portfolioId);

        if(ticker == null || ticker.isEmpty()) {
            transactions = transactionsRepository.findTransactionsByPortfolio(portfolioId);
        } else {
            transactions = transactionsRepository.findTransactionsByPortfolioAndTicker(portfolioId, ticker);
        }

        return transactions.stream().map(TransactionsDto::new).toList();
    }

    @Transactional
    public List<Transaction> postTransactionHistory(
            InputStream csvFile, Long portfolioId
    ) {
        portfolioService.findOrThrow(portfolioId);

        List<Transaction> importedTransactions = csvReaderService
                .getTransactionsFromCsv(csvFile, portfolioId);

        System.out.println("$postTransactionHistory - Persisting transactions...");
        transactionsRepository.persist(importedTransactions);

        // TODO: Avoid duplicate values, on duplicate requests.
        return importedTransactions;
    }
}
