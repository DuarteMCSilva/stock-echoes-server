package com.stockechoes.domain.dao;

import com.stockechoes.domain.model.Portfolio;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PortfolioRepository implements PanacheRepository<Portfolio> {

    public Portfolio getPortfolioById(String id) {
        return Portfolio.findById(id);
    }

    public Portfolio createPortfolio(Portfolio portfolio) {
        Portfolio.persist(portfolio);
        return portfolio;
    }
}
