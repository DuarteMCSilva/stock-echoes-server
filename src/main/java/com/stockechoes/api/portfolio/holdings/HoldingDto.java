package com.stockechoes.api.portfolio.holdings;

import com.stockechoes.api.market.tickers.Ticker;
import com.stockechoes.api.market.tickers.TickerDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class HoldingDto {

    private int quantity;

    private BigDecimal avgCost;

    private TickerDto ticker;

    public HoldingDto(
            Ticker ticker,
            int quantity, BigDecimal avgCost
    ) {
        this.ticker = new TickerDto(ticker);
        this.quantity = quantity;
        this.avgCost = avgCost;
    }
}
