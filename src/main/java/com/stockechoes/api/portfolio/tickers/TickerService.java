package com.stockechoes.api.portfolio.tickers;

import com.stockechoes.services.business.enrichment.TickerEnrichmentService;
import com.stockechoes.services.business.isin.IsinRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class TickerService {

    @Inject
    TickerRepository tickerRepository;

    @Inject
    TickerEnrichmentService tickerEnrichmentService;

    @Transactional
    public void prepareTickerByIsin(String isin) {
        Optional<Ticker> tickerOp = tickerRepository.findByIdOptional(isin);

        if(tickerOp.isPresent()) {
            return;
        }

        Ticker newTicker = new Ticker(isin);
        tickerRepository.persist(newTicker);
        tickerEnrichmentService.enrichTickerByIsin(isin);
    }

    @Transactional
    public void enrichTickerFromIsinRecord(IsinRecord isinRecord) {
        String isin = isinRecord.getIsin();
        Optional<Ticker> ticker = tickerRepository.findByIdOptional(isin);

        if(ticker.isPresent()) {
            ticker.get().setCompanyName(isinRecord.getName());
            ticker.get().setSymbol(isinRecord.getTicker());
            ticker.get().setSecurityType(isinRecord.getSecurityType());
        } else {
            Ticker t = new Ticker(isin, isinRecord);
            tickerRepository.persist(t);
        }
        System.out.println("Persisting: " + isinRecord.getTicker() + " " + isinRecord.getIsin());
    }
}
