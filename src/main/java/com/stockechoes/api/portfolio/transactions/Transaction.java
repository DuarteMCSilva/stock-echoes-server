package com.stockechoes.api.portfolio.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stockechoes.api.portfolio.portfolios.Portfolio;
import com.stockechoes.api.market.tickers.Ticker;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@ToString(exclude = {"ticker", "portfolio"})
@Entity(name = "se_transactions")
@Table(name = "se_transactions")
@EqualsAndHashCode(callSuper = true)
public class Transaction extends PanacheEntity {

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="portfolio_id", nullable = false)
    private Portfolio portfolio;

    private LocalDate date;

    private String isin;

    private int quantity;

    private BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "ticker_id")
    private Ticker ticker;
}
