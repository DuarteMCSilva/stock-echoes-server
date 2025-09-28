package com.stockechoes.api.tickers;

import com.stockechoes.services.business.enrichment.TickerEnrichmentService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class TickerService {

    @Inject
    TickerRepository tickerRepository;

    @Inject
    TickerEnrichmentService tickerEnrichmentService;

    // TODO: Optimize by making isin the Id.
    // TODO: Optimize by enriching in a list, minimizing requests.
    public Ticker prepareTickerByIsin(String isin) {
        Optional<Ticker> tickerOp = tickerRepository.findByIsin(isin);

        if(tickerOp.isPresent()) {
            return tickerOp.get();
        }

        Ticker newTicker = new Ticker(isin);
        tickerRepository.persist(newTicker);
        tickerEnrichmentService.enrichTickerByIsin(isin);
        return newTicker;
    }
}
