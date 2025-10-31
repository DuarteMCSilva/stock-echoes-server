package com.stockechoes.api.transactions;

import com.stockechoes.api.portfolio.portfolios.PortfolioRepository;
import com.stockechoes.api.portfolio.portfolios.exceptions.PortfolioExceptions;
import com.stockechoes.api.portfolio.transactions.TransactionsDto;
import com.stockechoes.api.portfolio.transactions.TransactionsService;
import com.stockechoes.services.business.enrichment.TickerEnrichmentService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
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

    @InjectMock
    @SuppressWarnings("unused")
    TickerEnrichmentService tickerEnrichmentService;

    @Test
    @DisplayName("Should save transactions linked to a portfolio")
    @TestTransaction
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
    @TestTransaction
    void portfolioNotFoundTest() {
        InputStream inputStream = getClass().getResourceAsStream("/data/transactions.csv");
        assertNotNull(inputStream, "CSV file not found in resources!");

        assertThrows(
                PortfolioExceptions.PortfolioNotFoundException.class,
                () -> transactionsService.postTransactionHistory(inputStream, 1L)
        );
    }
}
