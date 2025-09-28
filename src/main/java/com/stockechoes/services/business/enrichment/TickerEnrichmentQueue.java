package com.stockechoes.services.business.enrichment;

import com.stockechoes.api.tickers.Ticker;
import com.stockechoes.api.tickers.TickerRepository;
import com.stockechoes.services.business.isin.IsinRecord;
import com.stockechoes.services.business.isin.IsinRecordService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.Optional;

@ApplicationScoped
public class TickerEnrichmentQueue {

    @Inject
    IsinRecordService isinRecordService;

    @Inject
    TickerRepository tickerRepository;

    @Incoming("ticker-enrichment")
    @Transactional
    void tickerEnrichmentByIsin(String isin) {
        IsinRecord isinRecord = isinRecordService.fetchCompanyByIsin(isin);

        // TODO: In case of non-existence should not be responsibility of this service to create it...
        Optional<Ticker> ticker = tickerRepository.findByIsin(isin);

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
