package com.stockechoes.api.prices;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PriceEntryDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private LocalDate date;
    private Float closePrice;

    public PriceEntryDto (LocalDate s) { }

    @JsonCreator
    public PriceEntryDto (@JsonProperty("date") LocalDate date,
                          @JsonProperty("close") Float closePrice) {
        this.date = date;
        this.closePrice = closePrice;
    }
}
