package com.stockechoes.api.tickers;

import com.stockechoes.services.business.enrichment.TickerEnrichmentService;
import com.stockechoes.services.business.isin.IsinRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class TickerService {

    @Inject
    TickerRepository tickerRepository;

    @Inject
    TickerEnrichmentService tickerEnrichmentService;

    public void prepareTickerByIsin(String isin) {
        Optional<Ticker> tickerOp = tickerRepository.findByIdOptional(isin);

        if(tickerOp.isPresent()) {
            return;
        }

        Ticker newTicker = new Ticker(isin);
        tickerRepository.persist(newTicker);
        tickerEnrichmentService.enrichTickerByIsin(isin);
    }

    public void enrichTickerFromIsinRecord(IsinRecord isinRecord) {
        String isin = isinRecord.getIsin();
        Optional<Ticker> ticker = tickerRepository.findByIdOptional(isin);

        if(ticker.isPresent()) {
            ticker.get().setCompanyName(isinRecord.getName());
            ticker.get().setSymbol(isinRecord.getTicker());
        } else {
            Ticker t = new Ticker(isin, isinRecord.getTicker(), isinRecord.getName());
            tickerRepository.persist(t);
        }

        System.out.println("Ticker saved!: " + isinRecord.getTicker() + " " + isinRecord.getIsin());
    }
}
