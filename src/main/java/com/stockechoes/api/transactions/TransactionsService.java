package com.stockechoes.api.transactions;

import com.stockechoes.api.portfolios.PortfolioService;
import com.stockechoes.services.business.isin.IsinMapperService;
import com.stockechoes.services.utility.csv.CsvReaderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.InputStream;
import java.util.List;

@ApplicationScoped
public class TransactionsService {

    @Inject
    TransactionsDao transactionsDao;

    @Inject
    TransactionsRepository transactionsRepository;

    @Inject
    PortfolioService portfolioService;

    @Inject
    CsvReaderService csvReaderService;

    @Inject
    IsinMapperService isinMapperService; // TODO

    public List<TransactionsDto> getPortfolioTransactions(
            Long portfolioId, String ticker
    ) {
        portfolioService.findOrThrow(portfolioId);

        if(ticker == null || ticker.isEmpty()) {
            return transactionsDao.getPortfolioTransactions(portfolioId);
        }

        return transactionsDao.getPortfolioTransactions(portfolioId,ticker);
    }

    public List<Transaction> postTransactionHistory(
            InputStream csvFile, Long portfolioId
    ) {
        portfolioService.findOrThrow(portfolioId);

        List<Transaction> importedTransactions = csvReaderService
                .getTransactionsFromCsv(csvFile, portfolioId);

        transactionsRepository.persist(importedTransactions);

        // TODO: Avoid duplicate values, on duplicate requests.
        return importedTransactions;
    }
}
