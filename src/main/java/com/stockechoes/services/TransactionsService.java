package com.stockechoes.services;

import com.stockechoes.api.IsinMapperService;
import com.stockechoes.api.dto.IsinMapDto;
import com.stockechoes.client.form.FileUploadBody;
import com.stockechoes.domain.dao.PortfolioDao;
import com.stockechoes.domain.dao.TransactionsDao;
import com.stockechoes.domain.dto.TransactionsDto;
import com.stockechoes.domain.model.Portfolio;
import com.stockechoes.domain.model.TransactionEntry;
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
    PortfolioDao portfolioDao;

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
            FileUploadBody body, String portfolioId) {

        if( body.columnMetaData == null) {
            return Response.ok("No data").build();
        }

        List<String> metadataList = body.getColumnNameList();
        List<List<String>> table = csvReaderService.getTableFromCsv(body.file, metadataList);

        Portfolio portfolio = portfolioDao.getPortfolioById(portfolioId);

        for( List<String> row : table ) {
            TransactionEntry transactionEntry = getTransactionFromRow(row);
        }


        return Response.ok("Lines saved: " + table.size()).build();
    }

    private TransactionEntry getTransactionFromRow(List<String> row) {
        IsinMapDto isinMap = isinMapperService.fetchIsinMap();
        return new TransactionEntry();
    }
}
