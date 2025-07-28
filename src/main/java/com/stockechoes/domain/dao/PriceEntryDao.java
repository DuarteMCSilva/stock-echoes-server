package com.stockechoes.domain.dao;

import com.stockechoes.domain.dto.PriceEntryDto;
import com.stockechoes.domain.model.PriceEntry;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.*;

@ApplicationScoped
public class PriceEntryDao {

    public List<PriceEntryDto> getPricesByTicker(String symbol) {
        String statement =
                "SELECT pe_table.date, pe_table.price " +
                        "FROM PriceEntry pe_table " +
                        "JOIN ticker_table t_table ON pe_table.ticker.id = t_table.id " +
                        "WHERE t_table.symbol = ?1 " +
                        "ORDER BY pe_table.date ASC";

        return PriceEntry.find(statement, symbol)
                .project(List.class).stream()
                .map(row -> {
                    LocalDate date = (LocalDate) row.get(0);
                    Float price = (Float) row.get(1);
                    return new PriceEntryDto(date, price);
                }).toList();
    }
}
