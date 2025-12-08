package com.stockechoes.api.transactions;

import com.stockechoes.api.accounts.AccountService;
import com.stockechoes.api.auth.AuthContext;
import com.stockechoes.api.portfolio.portfolios.Portfolio;
import com.stockechoes.api.portfolio.portfolios.PortfolioRepository;
import com.stockechoes.api.portfolio.portfolios.exceptions.PortfolioExceptions;
import com.stockechoes.api.portfolio.transactions.TransactionsDto;
import com.stockechoes.api.portfolio.transactions.TransactionsService;
import com.stockechoes.services.business.enrichment.TickerEnrichmentService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@QuarkusTest
public class TransactionsServiceTest {

    @Inject
    TransactionsService transactionsService;

    @Inject
    PortfolioRepository portfolioRepository;

    @InjectMock
    @SuppressWarnings("unused")
    TickerEnrichmentService tickerEnrichmentService;

    @InjectMock
    AuthContext authContext;

    @Test
    @Disabled
    @DisplayName("Should save transactions linked to a portfolio")
    @TestTransaction
    void postTransactionHistoryTest() {
        // Given
        when(authContext.getAccountId()).thenReturn(10001L);

        InputStream inputStream = getClass().getResourceAsStream("/data/transactions.csv");
        Portfolio portfolio = portfolioRepository.findById(10002L);

        assertNotNull(inputStream, "CSV file not found in resources!");

        // When
        transactionsService.postTransactionHistory(inputStream, portfolio.getId());

        // Then
        List<TransactionsDto> savedTransactions = transactionsService.getPortfolioTransactions(portfolio.getId());
        assertEquals(18, savedTransactions.size());
    }

    @Test
    @DisplayName("Should throw when portfolio is not found")
    @TestTransaction
    void portfolioNotFoundTest() {
        when(authContext.getAccountId()).thenReturn(10001L);

        InputStream inputStream = getClass().getResourceAsStream("/data/transactions.csv");
        assertNotNull(inputStream, "CSV file not found in resources!");

        assertThrows(
                PortfolioExceptions.PortfolioNotFoundException.class,
                () -> transactionsService.postTransactionHistory(inputStream, 123456L)
        );
    }
}
