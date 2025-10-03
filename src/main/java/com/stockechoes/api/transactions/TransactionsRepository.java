package com.stockechoes.api.transactions;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class TransactionsRepository implements PanacheRepository<Transaction> {

    public List<Transaction> findTransactionsByPortfolio(Long id) {
        return find("portfolio.id = ?1 ORDER BY date ASC", id).list();
    }

    public List<Transaction> findTransactionsByPortfolioAndTicker(Long id, String isin) {
        return find("portfolio.id = ?1 and isin = ?2 ORDER BY date ASC", id, isin).list();
    }
}
