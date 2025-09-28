package com.stockechoes.api.tickers;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class TickerTests {

    @Inject
    TickerRepository tickerRepository;

    @Test
    void getByIsin() {
        Optional<Ticker> ticker = tickerRepository.findByIsin("US1");

        assertTrue(ticker.isPresent());
    }

    @Test
    @Transactional
    void persist_and_getByIsin() {
        String isin = "MockISIN";
        tickerRepository.persist(new Ticker(isin));
        Optional<Ticker> ticker = tickerRepository.findByIsin(isin);

        assertTrue(ticker.isPresent());
    }
}
