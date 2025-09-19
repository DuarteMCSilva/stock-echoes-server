package com.stockechoes.api.transactions;

import com.stockechoes.api.portfolios.Portfolio;
import com.stockechoes.api.tickers.Ticker;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity(name = "transaction_table")
@Table(name = "transaction_table")
public class Transaction extends PanacheEntity {

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name="portfolio_id")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "ticker_id")
    private Ticker ticker;

    private int quantity;

    private float cost;

}
