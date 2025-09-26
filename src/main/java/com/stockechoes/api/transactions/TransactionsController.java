package com.stockechoes.api.transactions;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@ApplicationScoped
@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
@SuppressWarnings("unused")
public class TransactionsController {

    @Inject
    TransactionsService transactionsService;

    @Inject
    TransactionsDao transactionsDao;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public List<TransactionsDto> getAllTransactionsByTicker(
            @QueryParam("portfolio") Long portfolioId,
            @QueryParam("ticker") String ticker
    ) {
        return transactionsService.getPortfolioTransactions(portfolioId, ticker);
    }

    @GET
    @Path("all") // No business case, only debugging.
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Transaction> getAllTransactions() {
        return transactionsDao.getTransactions();
    }


    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public Response saveTransactionHistory(
            @RestForm("file") @PartType(MediaType.APPLICATION_OCTET_STREAM) File file,
            @RestForm("portfolioId") Long portfolioId
    ) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(file);

        if(file.length() == 0) {
            throw new FileNotFoundException("Empty File");
        }
        List<Transaction> imported = transactionsService.postTransactionHistory(inputStream, portfolioId);
        return Response.ok("Lines saved: " + imported.size()).build();
    }
}
