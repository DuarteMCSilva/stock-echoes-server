package com.stockechoes.services.enrichment;


import com.stockechoes.api.tickers.Ticker;
import com.stockechoes.api.tickers.TickerRepository;
import com.stockechoes.services.business.enrichment.TickerEnrichmentService;
import com.stockechoes.services.business.isin.IsinRecord;
import com.stockechoes.services.business.isin.IsinRecordService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@QuarkusTest
public class TickerEnrichmentTests {

    @Inject
    TickerRepository tickerRepository;

    @Inject
    TickerEnrichmentService tickerEnrichmentService;

    @InjectMock
    IsinRecordService isinRecordService;

    @Test
    @DisplayName("Should enrich existing isin asynchronously")
    @Transactional
    void enrichTickerData() throws InterruptedException {
        // Given
        String isin = "US0605051046";
        tickerRepository.persist(new Ticker(isin));
        when(isinRecordService.fetchCompanyByIsin(isin)).thenReturn(getMockIsinRecord(isin));

        // When
        tickerEnrichmentService.enrichTickerByIsin(isin);

        Thread.sleep(1000); // TODO: Use await() pattern instead.

        // Then
        Ticker persisted = tickerRepository.findByIdOptional(isin)
                .orElseThrow(() -> new AssertionError("Ticker with isin " + isin + " not found"));

        assertEquals("BAC", persisted.getSymbol());
    }

    @Test
    @DisplayName("Should enrich non-existent ticker asynchronously")
    @Transactional
    void enrichTickerData_create() throws InterruptedException {
        // Given
        String isin = "US0605051046";
        when(isinRecordService.fetchCompanyByIsin(isin)).thenReturn(getMockIsinRecord(isin));

        // When
        tickerEnrichmentService.enrichTickerByIsin(isin);

        Thread.sleep(1000);

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
