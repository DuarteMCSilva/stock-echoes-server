package com.stockechoes.api.portfolio.transactions;

import com.stockechoes.api.portfolio.holdings.HoldingDto;
import com.stockechoes.api.portfolio.tickers.Ticker;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
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

    public List<HoldingDto> getHoldings(Long portfolioId) {
        String statement = "SELECT ticker, " +
                " SUM(quantity) as quantity, SUM(cost) as avgCost" + // TODO: Currently total cost, not avg!
                " FROM se_transactions tr" +
                " WHERE tr.portfolio.id = ?1 AND quantity > 0 " +
                " GROUP BY ticker";

        return transactionsRepository.find(statement, portfolioId)
                .project(List.class).stream()
                .map( (tr) -> {
                    Ticker symbol = (Ticker) tr.get(0);
                    int quantity = ( (Long) tr.get(1) ).intValue();
                    BigDecimal avgCost = (BigDecimal) tr.get(2);
                    return new HoldingDto(symbol, quantity, avgCost);
                }).toList();
    }
}
