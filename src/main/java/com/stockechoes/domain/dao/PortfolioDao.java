package com.stockechoes.domain.dao;

import com.stockechoes.domain.model.Portfolio;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class PortfolioDao {

    @PersistenceContext
    EntityManager entityManager;

    public List<Portfolio> getAllPortfolios() {
        return entityManager.createQuery("FROM Portfolio", Portfolio.class).getResultList();
    }
}
