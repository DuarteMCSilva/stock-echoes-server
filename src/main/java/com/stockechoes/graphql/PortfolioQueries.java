package com.stockechoes.graphql;

import com.stockechoes.domain.dao.TransactionsDao;
import com.stockechoes.domain.dto.HoldingDto;
import com.stockechoes.domain.dto.TransactionsDto;
import com.stockechoes.domain.model.Portfolio;
import com.stockechoes.services.HoldingsService;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;

import java.util.List;

@GraphQLApi()
public class PortfolioQueries {

    // https://piotrminkowski.com/2021/04/14/advanced-graphql-with-quarkus/

    @Inject
    TransactionsDao transactionsDao;

    @Inject
    HoldingsService holdingsService;

    @Query("portfolio")
    public Portfolio getPortfolio(@Name("id") String id) {
        return Portfolio.findById(id);
    }

    @Query("transactions")
    public List<TransactionsDto> getTransactions(@Source Portfolio portfolio) {
        return transactionsDao.getPortfolioTransactions(String.valueOf(portfolio.id));
    }

    @Query("holdings")
    public List<HoldingDto> getHoldings(@Source Portfolio portfolio) {
        return holdingsService.getCurrentHoldings(String.valueOf(portfolio.id));
    }
}
