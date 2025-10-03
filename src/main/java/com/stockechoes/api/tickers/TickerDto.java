package com.stockechoes.api.tickers;

import lombok.Data;

@Data
public class TickerDto {

    private String symbol;
    private String companyName;

    public TickerDto(Ticker ticker) {
        this.symbol = ticker.getSymbol();
        this.companyName = ticker.getCompanyName();
    }
}
