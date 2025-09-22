package com.stockechoes.api.transactions;

import com.stockechoes.api.holdings.HoldingDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class TransactionsDao {

    // see CriteriaQuery to build queries

    @Inject
    @SuppressWarnings("unused")
    private TransactionsRepository transactionsRepository;

    public List<Transaction> getTransactions() {
        return transactionsRepository.listAll();
    }

    public List<TransactionsDto> getPortfolioTransactions(Long portfolioId) {
        String statement = "SELECT t.date, t.quantity, t.cost, t.isin " +
                " FROM transaction_table t " +
                " WHERE portfolio.id = ?1 " +
                " ORDER BY t.date ASC";

        return transactionsRepository.find(statement, portfolioId )
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
                " WHERE portfolio.id = ?1 AND isin = ?2 " + // TODO: ISIN or Ticker symbol?
                " ORDER BY t.date ASC";

        return transactionsRepository.find(statement, portfolioId, ticker )
                .project(List.class).stream()
                .map( row -> {
                    LocalDate date = (LocalDate) row.get(0);
                    int quantity = (int) row.get(1);
                    BigDecimal cost = (BigDecimal) row.get(2);

                    return new TransactionsDto(date, ticker, quantity, cost);

                }).toList();
    }

    public List<HoldingDto> getHoldings(String portfolioId) {
        String statement = "SELECT isin, " +
                " SUM(quantity) as quantity, SUM(cost) as avgCost" +
                " FROM transaction_table tr" +
                " WHERE tr.portfolio.id = ?1 AND quantity > 0 " +
                " GROUP BY isin";

        return transactionsRepository.find(statement, portfolioId)
                .project(List.class).stream()
                .map( (tr) -> {
                    String symbol = (String) tr.get(0);
                    int quantity = ( (Long) tr.get(1) ).intValue();
                    BigDecimal avgCost = (BigDecimal) tr.get(2);
                    return new HoldingDto(symbol, quantity, avgCost);
                }).toList();
    }
}
