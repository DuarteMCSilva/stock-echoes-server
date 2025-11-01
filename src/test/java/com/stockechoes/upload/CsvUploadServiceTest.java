package com.stockechoes.upload;

import com.stockechoes.api.portfolio.portfolios.Portfolio;
import com.stockechoes.api.portfolio.transactions.Transaction;
import com.stockechoes.services.utility.csv.CsvReaderService;
import com.stockechoes.services.utility.csv.TransactionImportEntity;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class CsvUploadServiceTest {

    @Inject
    CsvReaderService csvReaderService;

    @Test
    @DisplayName("getListFromCsv should return a list of TransactionImportEntities")
    void upload() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/data/transactions.csv");
        assertNotNull(inputStream, "CSV file not found in resources!");
        List<TransactionImportEntity> transactions = csvReaderService.getListFromCsv(inputStream);
        inputStream.close();

        assertEquals(18, transactions.size());
        assertEquals(BigDecimal.valueOf(278.59), transactions.getFirst().getTotalPrice());
    }

    @Test
    @TestTransaction
    @DisplayName("should convert TransactionImportEntity into Transaction")
    void converter() {
        TransactionImportEntity transactionImportEntity = getMockTransaction();

        Transaction transaction = csvReaderService.toEntity(transactionImportEntity, Portfolio.findById(10001L));

        assertEquals(10, transaction.getQuantity());
        assertEquals(BigDecimal.valueOf(331.00), transaction.getCost());

        assertEquals("USisin", transaction.getTicker().getIsin());
        assertEquals("Royal Catin", transaction.getPortfolio().getName());
    }

    private TransactionImportEntity getMockTransaction() {
        TransactionImportEntity transaction = new TransactionImportEntity();
        transaction.setIsin("USisin");
        transaction.setDate(LocalDate.parse("2020-10-26"));
        transaction.setQuantity(10);
        transaction.setUnitPrice(BigDecimal.valueOf(33.10));
        transaction.setTotalPrice(BigDecimal.valueOf(331.00));
        return transaction;
    }
}
