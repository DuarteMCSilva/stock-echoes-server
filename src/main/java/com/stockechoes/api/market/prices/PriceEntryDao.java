package com.stockechoes.api.market.prices;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.*;

@ApplicationScoped
public class PriceEntryDao {

    public List<PriceEntryDto> getPricesByTicker(String symbol) {
        String statement =
                "SELECT se_prices.date, se_prices.price " +
                        "FROM se_prices " +
                        "JOIN se_tickers t_table ON se_prices.ticker.id = t_table.id " +
                        "WHERE t_table.symbol = ?1 " +
                        "ORDER BY se_prices.date ASC";

        return PriceEntry.find(statement, symbol)
                .project(List.class).stream()
                .map(row -> {
                    LocalDate date = (LocalDate) row.get(0);
                    Float price = (Float) row.get(1);
                    return new PriceEntryDto(date, price);
                }).toList();
    }
}
