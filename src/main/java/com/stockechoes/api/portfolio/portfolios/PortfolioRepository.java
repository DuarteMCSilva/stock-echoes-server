package com.stockechoes.api.portfolio.portfolios;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class PortfolioRepository implements PanacheRepository<Portfolio> {
    public Optional<Portfolio> findDuplicateOptional(String name, Long accountId) {
        return find("name = ?1 AND accountId = ?2", name, accountId).firstResultOptional();
    }
}
