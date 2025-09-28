package com.stockechoes.api.tickers;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class TickerRepository implements PanacheRepository<Ticker> {

    public Optional<Ticker> findByIsin(String isin) {
        return find("isin", isin).firstResultOptional();
    }
}
