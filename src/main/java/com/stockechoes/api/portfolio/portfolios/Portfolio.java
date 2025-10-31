package com.stockechoes.api.portfolio.portfolios;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="portfolios_table")
public class Portfolio extends PanacheEntity {

    private String name;

    public Portfolio() { }

    public Portfolio(String name) {
        this.name = name;
    }
}
