package com.stockechoes.api.portfolios;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PortfolioRepository implements PanacheRepository<Portfolio> {

    public Long createPortfolio(String name) {
        Portfolio portfolio = new Portfolio(name);
        persist(portfolio);
        return portfolio.id;
    }
}
