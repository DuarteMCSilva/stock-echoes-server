package com.stockechoes.api.transactions;

import com.stockechoes.api.holdings.HoldingDto;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class TransactionsDao {

    // see CriteriaQuery to build queries

    public List<Transaction> getTransactions() {
        return Transaction.listAll();
    }

    public List<TransactionsDto> getPortfolioTransactions(Long portfolioId) {
        String statement = "SELECT t.date, t.quantity, t.cost, t.isin " +
                " FROM transaction_table t " +
                " WHERE portfolio.id = ?1 " +
                " ORDER BY t.date ASC";

        return Transaction.find(statement, portfolioId )
                .project(List.class).stream()
                .map( row -> {
                    LocalDate date = (LocalDate) row.get(0);
                    int quantity = (int) row.get(1);
                    BigDecimal cost = (BigDecimal) row.get(2);
                    String ticker = (String) row.get(3);

                    return new TransactionsDto(date, ticker, quantity, cost);
                }).toList();
    }

    public List<TransactionsDto> getPortfolioTransactions(Long portfolioId, String ticker) {
        String statement = "SELECT t.date, t.quantity, t.cost " +
                " FROM transaction_table t " +
                " WHERE portfolio.id = ?1 AND ticker.symbol = ?2 " +
                " ORDER BY t.date ASC";

        return Transaction.find(statement, portfolioId, ticker )
                .project(List.class).stream()
                .map( row -> {
                    LocalDate date = (LocalDate) row.get(0);
                    int quantity = (int) row.get(1);
                    BigDecimal cost = (BigDecimal) row.get(2);

                    return new TransactionsDto(date, ticker, quantity, cost);

                }).toList();
    }

    public List<HoldingDto> getHoldings(String portfolioId) {
        String statement = "SELECT tick.symbol, tick.company_name as name, " +
                " SUM(quantity) as quantity, SUM(cost * quantity)/SUM(quantity) as avgCost" +
                " FROM transaction_table tr" +
                " JOIN ticker_table tick on tick.id = tr.ticker.id" +
                " WHERE tr.portfolio.id = ?1 " +
                " GROUP BY tick.id";

        return Transaction.find(statement, portfolioId)
                .project(List.class).stream()
                .map( (tr) -> {
                    String symbol = (String) tr.get(0);
                    String name = (String) tr.get(1);
                    int quantity = ( (Long) tr.get(2) ).intValue();
                    double avgCost = (double) tr.get(3);
                    return new HoldingDto(symbol, name, quantity, avgCost);
                }).toList();
    }
}
