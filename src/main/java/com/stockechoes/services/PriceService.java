package com.stockechoes.services;

import com.stockechoes.api.WebScrapperService;
import com.stockechoes.domain.dao.PriceEntryDao;
import com.stockechoes.domain.dto.PriceEntryDto;
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
}
