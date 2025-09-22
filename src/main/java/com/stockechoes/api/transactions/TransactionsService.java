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
import java.util.Map;
import java.util.Optional;

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

        Optional<Portfolio> portfolio = portfolioService.getPortfolioById(portfolioId);

        if(portfolio.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of(
                            "error", "Portfolio not found",
                            "portfolioId", portfolioId
                    )).build();
        }

        List<Transaction> importedTransactions = csvReaderService
                .getTransactionsFromCsv(csvFile, portfolioId);

        transactionsRepository.persist(importedTransactions);

        // TODO: Avoid duplicate values, on duplicate requests.
        return Response.ok("Lines saved: " + importedTransactions.size()).build();

    }
}
