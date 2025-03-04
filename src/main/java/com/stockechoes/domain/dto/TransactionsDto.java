package com.stockechoes.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionsDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private LocalDate date;

    private String ticker;

    private int quantity;

    private float cost;

    public TransactionsDto(
            LocalDate date, String ticker,
            int quantity, float cost) {
        this.date = date;
        this.ticker = ticker;
        this.quantity = quantity;
        this.cost = cost;
    }
}
