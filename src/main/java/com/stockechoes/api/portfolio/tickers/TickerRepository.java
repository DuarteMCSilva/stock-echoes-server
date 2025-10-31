package com.stockechoes.api.portfolio.tickers;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TickerRepository implements PanacheRepositoryBase<Ticker,String> {
}
