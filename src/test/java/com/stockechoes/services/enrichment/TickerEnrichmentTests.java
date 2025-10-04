package com.stockechoes.services.enrichment;


import com.stockechoes.api.tickers.Ticker;
import com.stockechoes.api.tickers.TickerRepository;
import com.stockechoes.services.business.enrichment.TickerEnrichmentQueue;
import com.stockechoes.services.business.isin.IsinRecord;
import com.stockechoes.services.business.isin.IsinRecordService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@QuarkusTest
public class TickerEnrichmentTests {

    @Inject
    TickerRepository tickerRepository;

    @Inject
    TickerEnrichmentQueue tickerEnrichmentQueue;

    @InjectMock
    IsinRecordService isinRecordService;

    @Test
    @DisplayName("Should enrich existing isin asynchronously")
    @TestTransaction
    void enrichTickerData() {
        // Given
        String isin = "US1";
        when(isinRecordService.fetchCompanyByIsin(isin)).thenReturn(getMockIsinRecord(isin));

        // When
        tickerEnrichmentQueue.tickerEnrichmentByIsin(isin);

        // Then
        Ticker persisted = tickerRepository.findByIdOptional(isin)
                .orElseThrow(() -> new AssertionError("Ticker with isin " + isin + " not found"));

        assertEquals("BAC", persisted.getSymbol());
    }

    @Test
    @DisplayName("Should enrich non-existent ticker asynchronously")
    @TestTransaction
    void enrichTickerData_create() {
        // Given
        String isin = "US0605051046";
        when(isinRecordService.fetchCompanyByIsin(isin)).thenReturn(getMockIsinRecord(isin));

        // When
        tickerEnrichmentQueue.tickerEnrichmentByIsin(isin);

        // Then
        Ticker persisted = tickerRepository.findByIdOptional(isin)
                .orElseThrow(() -> new AssertionError("Ticker with isin " + isin + " not found"));

        assertEquals("BAC", persisted.getSymbol());
    }


    private IsinRecord getMockIsinRecord(String isin) {
        IsinRecord record = new IsinRecord();
        record.setIsin(isin);
        record.setName("Bank of America");
        record.setTicker("BAC");

        return record;
    }

}
