package com.stockechoes.api.holdings;

import com.stockechoes.api.tickers.Ticker;
import com.stockechoes.api.tickers.TickerDto;
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
