package com.stockechoes.api.market.prices;

import com.stockechoes.api.market.prices.dto.IntradayPriceDto;
import com.stockechoes.api.market.prices.dto.IntradayPriceForm;
import com.stockechoes.services.api.WebScrapperService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@ApplicationScoped
public class PriceService {

    @ConfigProperty(name = "stock-echoes.scrapper.url")
    String webScrapperUrl;

    @Inject
    PriceEntryDao priceEntryDao;

    @Inject
    WebScrapperService webScrapperService;

    public List<PriceEntryDto> getPricesByTicker(String symbol) {
        List<PriceEntryDto> results = priceEntryDao.getPricesByTicker(symbol).stream().toList();

        if(results.isEmpty()){
            results = getPricesByTickerFromWeb(symbol);
        }

        return results;
    }

    public List<PriceEntryDto> getPricesByTickerFromWeb(String symbol) {
        return webScrapperService.fetchPriceHistory(symbol, "1y");
    }

    public List<IntradayPriceDto> getEodPricesForTickerList(IntradayPriceForm body) {

        if(body.getTickers().isEmpty()) {
            return List.of();
        }

        return webScrapperService.fetchEodPrices(body.getTickers());
    }
}
