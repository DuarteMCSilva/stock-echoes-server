package com.stockechoes.api.market.prices;

import com.stockechoes.api.portfolio.tickers.Ticker;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@Entity
@Table(name="price_entry")
@EqualsAndHashCode(callSuper = true)
public class PriceEntry extends PanacheEntity {

    private float price;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "ticker_id")
    public Ticker ticker;

    public PriceEntry() { };

    public PriceEntry(float price, LocalDate date) { }
}
