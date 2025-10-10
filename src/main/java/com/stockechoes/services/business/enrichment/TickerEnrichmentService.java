package com.stockechoes.services.business.enrichment;

import com.stockechoes.api.tickers.TickerService;
import com.stockechoes.services.business.isin.IsinRecord;
import com.stockechoes.services.business.isin.IsinRecordService;
import com.stockechoes.services.utility.eventBuffer.EventBuffer;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TickerEnrichmentService {

    @Inject
    IsinRecordService isinRecordService;

    @Inject
    TickerService tickerService;

    EventBuffer<String> eventBuffer;

    public void enrichTickerByIsin(String isin) {
        if(eventBuffer == null) {
            this.eventBuffer = new EventBuffer<>(2, 1000);
            this.subscribe();
            System.out.println("Ticker enrichment process has started.");
        }
        eventBuffer.emit(isin);
    }

    public void subscribe() {
        Multi<List<String>> multi = this.eventBuffer.getMulti();

        multi.subscribe().with(
                this::batchTickerEnrichment,
                System.out::println,
                () -> {
                    System.out.println("Ticker enrichment process completed!");
                    this.eventBuffer = null;
                }
        );
    }

    @Transactional
    public void batchTickerEnrichment(List<String> isinBatch) {
        List<IsinRecord> isinRecord = isinRecordService.fetchCompanyByIsinList(isinBatch);

        isinRecord.forEach( (record) -> tickerService.enrichTickerFromIsinRecord(record));
    }
}
