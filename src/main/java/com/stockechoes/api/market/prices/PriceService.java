package com.stockechoes.api.market.prices;

import com.stockechoes.api.market.prices.dto.IntradayPriceDto;
import com.stockechoes.api.market.prices.dto.IntradayPriceForm;
import com.stockechoes.rest.yfinance.YFinanceService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PriceService {

    @Inject
    PriceEntryDao priceEntryDao;

    @Inject
    YFinanceService YFinanceService;

    public List<PriceEntryDto> getPricesByTicker(String symbol) {
        List<PriceEntryDto> results = priceEntryDao.getPricesByTicker(symbol).stream().toList();

        if(results.isEmpty()){
            results = getPricesByTickerFromWeb(symbol);
        }

        return results;
    }

    public List<PriceEntryDto> getPricesByTickerFromWeb(String symbol) {
        return YFinanceService.fetchPriceHistory(symbol, "1y");
    }

    public List<IntradayPriceDto> getEodPricesForTickerList(IntradayPriceForm body) {

        if(body.getTickers().isEmpty()) {
            return List.of();
        }

        return YFinanceService.fetchEodPrices(body.getTickers());
    }
}
