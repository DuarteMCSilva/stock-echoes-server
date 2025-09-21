package com.stockechoes.services.utility.csv;

import java.io.*;
import java.util.*;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.stockechoes.api.portfolios.Portfolio;
import com.stockechoes.api.transactions.Transaction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CsvReaderService {

    @Inject
    EntityManager em;

    public List<Transaction> getTransactionsFromCsv(InputStream stream, Long portfolioId ) {
        return getListFromCsv(stream).stream().map((tie) -> toEntity(tie, portfolioId)).toList();
    }

    public List<TransactionImportEntity> getListFromCsv(InputStream stream) {
        InputStreamReader streamReader = new InputStreamReader(stream);
        CsvToBean<TransactionImportEntity> csvToBean = new CsvToBeanBuilder<TransactionImportEntity>(streamReader)
                .withType(TransactionImportEntity.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        return csvToBean.parse();
    }

    @Transactional
    public Transaction toEntity(TransactionImportEntity dto, Long portfolioId) {
        Transaction tx = new Transaction();
        tx.setDate(dto.getDate());
        tx.setQuantity(dto.getQuantity());
        tx.setCost(dto.getTotalPrice());
        tx.setIsin(dto.getIsin());

        // Instead of loading Portfolio with find(), just create a proxy by id:
        Portfolio portfolioRef = this.em.getReference(Portfolio.class, portfolioId);
        tx.setPortfolio(portfolioRef);
        return tx;
    }
}
