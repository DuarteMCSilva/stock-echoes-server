package com.stockechoes.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="ticker_table")
public class Ticker extends PanacheEntity {

    private String symbol;
    private String company_name;

    public Ticker() { };

    public Ticker(String symbol) { }

    public Ticker(String symbol, String company_name) { }
}
