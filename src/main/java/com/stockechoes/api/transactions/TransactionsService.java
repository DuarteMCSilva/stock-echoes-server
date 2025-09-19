package com.stockechoes.api.transactions;

import com.stockechoes.services.business.isin.IsinMapperService;
import com.stockechoes.services.business.isin.IsinMapDto;
import com.stockechoes.client.form.FileUploadBody;
import com.stockechoes.services.utility.CsvReaderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class TransactionsService {

    @Inject
    TransactionsDao transactionsDao;

    @Inject
    CsvReaderService csvReaderService;

    @Inject
    IsinMapperService isinMapperService;

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
            FileUploadBody body) {
        String firstLine = "No data";

        if( body.columnMetaData != null) {
            List<String> metadataList = body.getColumnNameList();
            List<List<String>> table = csvReaderService.getTableFromCsv(body.file, metadataList);
            IsinMapDto isinMap = isinMapperService.fetchIsinMap();

            return Response.ok("Lines saved: " + table.size()).build();
        }

        return Response.ok(firstLine).build();
    }
}
