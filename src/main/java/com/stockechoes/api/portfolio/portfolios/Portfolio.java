package com.stockechoes.api.portfolio.portfolios;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="portfolios_table")
public class Portfolio extends PanacheEntityBase {

    @Id
    @GeneratedValue
    @JsonIgnore
    public Long id;

    @JsonIgnore
    public Long accountId;

    private String name;

    public Portfolio() { }

    public Portfolio(String name) {
        this.name = name;
    }
}
