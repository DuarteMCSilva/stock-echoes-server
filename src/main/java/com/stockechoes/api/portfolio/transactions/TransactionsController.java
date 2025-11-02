package com.stockechoes.api.portfolio.transactions;

import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

import java.io.*;
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
    public List<TransactionsDto> getAllTransactions(
            @QueryParam("portfolio") Long portfolioId
    ) {


        return transactionsService.getPortfolioTransactions(portfolioId);
    }


    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public Response saveTransactionHistory(
            @RestForm("file") @PartType(MediaType.APPLICATION_OCTET_STREAM) File file,
            @RestForm("portfolioId") Long portfolioId
    ) throws IOException {
        InputStream inputStream = new FileInputStream(file);

        if(file.length() == 0 || !file.isFile()) {
            throw new FileNotFoundException("Empty File");
        }
        List<Transaction> imported = transactionsService.postTransactionHistory(inputStream, portfolioId);
        inputStream.close();
        return Response.ok("Lines saved: " + imported.size()).build();
    }

    @GET
    @Path("all")
    @IfBuildProfile("dev") // TODO: ERASE - No use case, only debugging.
    @Consumes(MediaType.APPLICATION_JSON)
    public List<TransactionsDto> getAllTransactions() {
        return transactionsDao.getTransactions().stream().map(TransactionsDto::new).toList();
    }
}
