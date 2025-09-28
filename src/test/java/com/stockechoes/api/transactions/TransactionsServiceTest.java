package com.stockechoes.api.transactions;

import com.stockechoes.api.portfolios.PortfolioRepository;
import com.stockechoes.api.portfolios.exceptions.PortfolioExceptions;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TransactionsServiceTest {

    @Inject
    TransactionsService transactionsService;

    @Inject
    PortfolioRepository portfolioRepository;

    @Test
    @DisplayName("Should save transactions linked to a portfolio")
    @Transactional
    void postTransactionHistoryTest() {
        // Given
        InputStream inputStream = getClass().getResourceAsStream("/data/transactions.csv");
        Long existingId = portfolioRepository.createPortfolio("mock-ptf");
        assertNotNull(inputStream, "CSV file not found in resources!");
        assertNotNull(existingId);

        // When
        transactionsService.postTransactionHistory(inputStream, existingId);

        // Then
        List<TransactionsDto> savedTransactions = transactionsService.getPortfolioTransactions(existingId, null);
        assertEquals(18, savedTransactions.size());
    }

    @Test
    @DisplayName("Should throw when portfolio is not found")
    @Transactional
    void portfolioNotFoundTest() {
        InputStream inputStream = getClass().getResourceAsStream("/data/transactions.csv");
        assertNotNull(inputStream, "CSV file not found in resources!");

        assertThrows(
                PortfolioExceptions.PortfolioNotFoundException.class,
                () -> transactionsService.postTransactionHistory(inputStream, 1L)
        );
    }
}
