package com.stockechoes.api.portfolio.portfolios.dto;

import com.stockechoes.api.portfolio.portfolios.Portfolio;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PortfolioDto {

    private Long id;
    private String name;

    public PortfolioDto(Portfolio portfolio) {
        this.id = portfolio.getId();
        this.name = portfolio.getName();
    }
}
