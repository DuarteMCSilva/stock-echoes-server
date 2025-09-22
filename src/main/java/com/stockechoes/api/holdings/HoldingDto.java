package com.stockechoes.api.holdings;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HoldingDto {

    private String symbol;

    private int quantity;

    private BigDecimal avgCost;

    public HoldingDto(
            String symbol,
            int quantity, BigDecimal avgCost
    ) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.avgCost = avgCost;
    }
}
