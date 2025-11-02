package com.stockechoes.services.utility.csv;

import java.io.*;
import java.util.*;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.stockechoes.api.portfolio.portfolios.Portfolio;
import com.stockechoes.api.market.tickers.Ticker;
import com.stockechoes.api.market.tickers.TickerService;
import com.stockechoes.api.portfolio.transactions.Transaction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class CsvReaderService {

    @Inject
    EntityManager em;

    @Inject
    TickerService tickerService;

    public List<Transaction> getTransactionsFromCsv(InputStream stream, Portfolio portfolio ) {
        return getListFromCsv(stream).stream()
                .filter( (tie) -> !tie.getIsin().trim().isEmpty())
                .map((tie) -> {
                    tickerService.prepareTickerByIsin(tie.getIsin());
                    return toEntity(tie, portfolio);
                }).toList();
    }

    public List<TransactionImportEntity> getListFromCsv(InputStream stream) {
        InputStreamReader streamReader = new InputStreamReader(stream);
        CsvToBean<TransactionImportEntity> csvToBean = new CsvToBeanBuilder<TransactionImportEntity>(streamReader)
                .withType(TransactionImportEntity.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        return csvToBean.parse();
    }

    public Transaction toEntity(TransactionImportEntity dto, Portfolio portfolio) {
        Transaction tx = new Transaction();
        tx.setDate(dto.getDate());
        tx.setQuantity(dto.getQuantity());
        tx.setCost(dto.getTotalPrice());

        Ticker tickerRef = this.em.getReference(Ticker.class, dto.getIsin());
        tx.setTicker(tickerRef);
        // Instead of loading Portfolio with find(), just create a proxy by id:
        tx.setPortfolio(portfolio);
        return tx;
    }
}
