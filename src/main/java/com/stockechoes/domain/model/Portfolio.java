package com.stockechoes.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Portfolio extends PanacheEntity {

    public Portfolio(String name) {
    }
}
