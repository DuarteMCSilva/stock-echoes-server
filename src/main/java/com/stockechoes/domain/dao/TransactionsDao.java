package com.stockechoes.domain.dao;

import com.stockechoes.domain.dto.TransactionsDto;
import com.stockechoes.domain.model.Transaction;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class TransactionsDao {

    public List<Transaction> getTransactions() {
        return Transaction.listAll();
    }

    public List<TransactionsDto> getPortfolioTransactions(String portfolioId) {
        String statement = "SELECT t.date, t.quantity, t.cost, t.ticker.symbol " +
                " FROM Transaction t " +
                " WHERE portfolio.id = ?1 " +
                " ORDER BY t.date ASC";

        return Transaction.find(statement, portfolioId )
                .project(List.class).stream()
                .map( row -> {
                    LocalDate date = (LocalDate) row.get(0);
                    int quantity = (int) row.get(1);
                    float cost = (float) row.get(2);
                    String ticker = (String) row.get(3);

                    return new TransactionsDto(date, ticker, quantity, cost);
                }).toList();
    }

    public List<TransactionsDto> getPortfolioTransactions(String portfolioId, String ticker) {
        String statement = "SELECT t.date, t.quantity, t.cost " +
                " FROM Transaction t " +
                " WHERE portfolio.id = ?1 AND ticker.symbol = ?2 " +
                " ORDER BY t.date ASC";

        return Transaction.find(statement, portfolioId, ticker )
                .project(List.class).stream()
                .map( row -> {
                    LocalDate date = (LocalDate) row.get(0);
                    int quantity = (int) row.get(1);
                    float cost = (float) row.get(2);

                    return new TransactionsDto(date, ticker, quantity, cost);

                }).toList();
    }
}
