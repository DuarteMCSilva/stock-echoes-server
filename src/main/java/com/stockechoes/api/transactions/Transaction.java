package com.stockechoes.api.transactions;

import com.stockechoes.api.portfolios.Portfolio;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity(name = "transaction_table")
@Table(name = "transaction_table")
public class Transaction extends PanacheEntity {
    @ManyToOne
    @JoinColumn(name="portfolio_id")
    private Portfolio portfolio;

    private LocalDate date;

    private String isin;

    private int quantity;

    private BigDecimal cost;

    /*
    TODO: Do a later sync.
    Not practical, due to the possibility of non-existence in the db when
    the transaction csv is loaded.
    @ManyToOne
    @JoinColumn(name = "ticker_id")
    private Ticker ticker;
    */
}
