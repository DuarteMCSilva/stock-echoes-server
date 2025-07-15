package com.stockechoes.domain.dao;

import com.stockechoes.domain.model.Portfolio;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class PortfolioDao {

    @PersistenceContext
    EntityManager entityManager;

    public void createPortfolio(String name) {
        Portfolio portfolio = new Portfolio(name);
        Portfolio.persist(portfolio);
    }

    public Portfolio getPortfolioById(String id){
        return Portfolio.findById(id);
    }
}
