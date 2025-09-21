package com.stockechoes.api.transactions;

import com.stockechoes.api.portfolios.Portfolio;
import com.stockechoes.api.portfolios.PortfolioService;
import com.stockechoes.services.business.isin.IsinMapperService;
import com.stockechoes.services.utility.csv.CsvReaderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

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
        if(portfolioId == null) {
            throw new RuntimeException("No portfolio id was given");
        }

        if(ticker == null || ticker.isEmpty()) {
            return transactionsDao.getPortfolioTransactions(portfolioId);
        }

        return transactionsDao.getPortfolioTransactions(portfolioId,ticker);
    }

    public Response postTransactionHistory(InputStream csvFile, Long portfolioId) {

        Portfolio portfolio = portfolioService.ensurePortfolioPresence(portfolioId);

        if(portfolio == null) {
            return Response.notModified("Error creating portfolio").build();
        }

        List<Transaction> importedTransactions = csvReaderService.getTransactionsFromCsv(csvFile, portfolioId);

        transactionsRepository.persist(importedTransactions);

        // TODO: Avoid duplicate values, on duplicate requests.
        return Response.ok("Lines saved: " + importedTransactions.size()).build();

    }
}
