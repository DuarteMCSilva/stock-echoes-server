package com.stockechoes.resources;

import com.stockechoes.domain.dao.TransactionsDao;
import com.stockechoes.domain.dto.TransactionsDto;
import com.stockechoes.domain.model.Transaction;
import com.stockechoes.services.TransactionsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@ApplicationScoped
@Path("/transactions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionsResource {

    @Inject
    TransactionsService transactionsService;

    @Inject
    TransactionsDao transactionsDao;

    @GET
    public List<TransactionsDto> getAllTransactionsByTicker(
            @QueryParam("portfolio") String portfolioId,
            @QueryParam("ticker") String ticker
    ) {
        return transactionsService.getPortfolioTransactions(portfolioId, ticker);
    }

    @GET
    @Path("all") // No business case, only debugging.
    public List<Transaction> getAllTransactions() {
        return transactionsDao.getTransactions();
    }
}
