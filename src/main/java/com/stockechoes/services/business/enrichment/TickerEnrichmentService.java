package com.stockechoes.services.business.enrichment;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class TickerEnrichmentService {

    @Inject
    @Channel("ticker-enrichment")
    Emitter<String> emitter;

    public void enrichTickerByIsin(String isin) {
        this.emitter.send(isin);
    }
}
