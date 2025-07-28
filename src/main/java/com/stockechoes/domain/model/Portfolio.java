package com.stockechoes.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="portfolios_table")
public class Portfolio extends PanacheEntity {

    private String name;

    public Portfolio() { }

    public Portfolio(String name) { }
}
