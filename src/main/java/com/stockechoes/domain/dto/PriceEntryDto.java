package com.stockechoes.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PriceEntryDto {

    private LocalDate date;
    private Float price;

    public PriceEntryDto (LocalDate s) { }

    @JsonCreator
    public PriceEntryDto (@JsonProperty("date") LocalDate date,
                          @JsonProperty("price") Float price) {
        this.date = date;
        this.price = price;
    }
}
