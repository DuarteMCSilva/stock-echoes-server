package com.stockechoes.api.market.prices.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class IntradayPriceDto {
    private String symbol;
    private BigDecimal close;
    private BigDecimal change;
}
