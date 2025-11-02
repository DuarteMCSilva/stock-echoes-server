package com.stockechoes.api.portfolio.transactions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stockechoes.api.market.tickers.Ticker;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionsDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private LocalDate date;

    private Ticker ticker;

    private int quantity;

    private BigDecimal cost;

    public TransactionsDto(Transaction transaction) {
        this.date = transaction.getDate();
        this.ticker = transaction.getTicker();
        this.quantity = transaction.getQuantity();
        this.cost = transaction.getCost();
    }
}
