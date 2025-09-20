package com.stockechoes.api.transactions;

import com.stockechoes.services.business.isin.IsinMapperService;
import com.stockechoes.services.utility.csv.CsvReaderService;
import com.stockechoes.services.utility.csv.TransactionImportEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@ApplicationScoped
public class TransactionsService {

    @Inject
    TransactionsDao transactionsDao;

    @Inject
    CsvReaderService csvReaderService;

    @Inject
    IsinMapperService isinMapperService; // TODO

    public List<TransactionsDto> getPortfolioTransactions(
            String portfolioId, String ticker
    ) {
        if(portfolioId == null || portfolioId.isEmpty()) {
            throw new RuntimeException("No portfolio id was given");
        }

        if(ticker == null || ticker.isEmpty()) {
            return transactionsDao.getPortfolioTransactions(portfolioId);
        }

        return transactionsDao.getPortfolioTransactions(portfolioId,ticker);
    }

    public Response postTransactionHistory(
            InputStream csvFile) throws IOException {
        String firstLine = "No data";

        if(csvFile != null) {
            List<TransactionImportEntity> importedTransactions = csvReaderService.getListFromCsv(csvFile);
            return Response.ok("Lines saved: " + importedTransactions.size()).build();
        }

        return Response.ok(firstLine).build();
    }
}
