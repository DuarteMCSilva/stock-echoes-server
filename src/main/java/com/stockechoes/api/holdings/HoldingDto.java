package com.stockechoes.api.holdings;

import lombok.Data;

@Data
public class HoldingDto {

    private String symbol;

    private String name;

    private int quantity;

    private double avgCost;

    public HoldingDto(
            String symbol, String name,
            int quantity, double avgCost
    ) {
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.avgCost = avgCost;
    }
}
