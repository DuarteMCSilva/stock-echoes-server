package com.stockechoes.rest.yfinance;

import com.stockechoes.api.market.prices.PriceEntryDto;
import com.stockechoes.api.market.prices.dto.IntradayPriceDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

import static com.stockechoes.services.utility.JsonUtils.listFromJson;

@ApplicationScoped
public class YFinanceService {

    @Inject
    @RestClient
    YFinanceClient yFinanceClient;

    public List<PriceEntryDto> fetchPriceHistory(String ticker, String period) {
        String responseBody = yFinanceClient.fetchHistoricalPrices(ticker,period);

        return listFromJson(responseBody, PriceEntryDto.class);
    }

    public List<IntradayPriceDto> fetchEodPrices(List<String> tickers) {
        String param = String.join(",", tickers);
        String responseBody = yFinanceClient.fetchEodPrices(param);

        return listFromJson(responseBody, IntradayPriceDto.class);
    }
}
