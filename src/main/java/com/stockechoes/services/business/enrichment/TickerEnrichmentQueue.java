package com.stockechoes.services.business.enrichment;

import com.stockechoes.api.tickers.TickerService;
import com.stockechoes.services.business.isin.IsinRecord;
import com.stockechoes.services.business.isin.IsinRecordService;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TickerEnrichmentQueue {

    @Inject
    IsinRecordService isinRecordService;

    @Inject
    TickerService tickerService;

    private final List<String> buffer = new ArrayList<>();

    @Incoming("ticker-enrichment2")
    @Blocking
    public void batchTickerEnrichment(String isin) {
        buffer.add(isin);

        if (buffer.size() == 3) {
            this.batchTickerEnrichment(buffer);
            buffer.clear();
            System.out.println("AA");
        }
    }

    public Uni<Void> batchTickerEnrichment(List<String> isinBatch) {
        List<IsinRecord> isinRecord = isinRecordService.fetchCompanyByIsin(isinBatch);

        isinRecord.forEach( (record) -> tickerService.enrichTickerFromIsinRecord(record));
        return Uni.createFrom().nothing();
    }

    @Incoming("ticker-enrichment")
    @Transactional
    public void tickerEnrichmentByIsin(String isin) {
        IsinRecord isinRecord = isinRecordService.fetchCompanyByIsin(isin);

        tickerService.enrichTickerFromIsinRecord(isinRecord);
    }
}
