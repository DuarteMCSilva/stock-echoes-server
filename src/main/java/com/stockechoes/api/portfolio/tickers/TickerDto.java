package com.stockechoes.api.portfolio.tickers;

import lombok.Data;

@Data
public class TickerDto {

    private String symbol;
    private String companyName;
    private String securityType;

    public TickerDto(Ticker ticker) {
        this.symbol = ticker.getSymbol();
        this.companyName = ticker.getCompanyName();
        this.securityType = ticker.getSecurityType();
    }
}
