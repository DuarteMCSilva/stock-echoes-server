package com.stockechoes.api.accounts;

import com.stockechoes.api.accounts.customer.CustomerDto;
import com.stockechoes.api.portfolio.portfolios.Portfolio;
import com.stockechoes.api.portfolio.portfolios.dto.PortfolioDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountDto {

    private CustomerDto customer;

    private List<PortfolioDto> portfolios;

    public void setPortfolios(List<Portfolio> portfolios) {
        this.portfolios = portfolios.stream()
                .map((PortfolioDto::new)).toList();
    }
}
