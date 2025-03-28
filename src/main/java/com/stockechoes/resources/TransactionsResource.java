package com.stockechoes.resources;

import com.stockechoes.client.form.FileUploadBody;
import com.stockechoes.domain.dao.TransactionsDao;
import com.stockechoes.domain.dto.TransactionsDto;
import com.stockechoes.domain.model.TransactionEntry;
import com.stockechoes.services.TransactionsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.io.InputStream;
import java.util.List;

@ApplicationScoped
@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionsResource {

    @Inject
    TransactionsService transactionsService;

    @Inject
    TransactionsDao transactionsDao;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public List<TransactionsDto> getAllTransactionsByTicker(
            @QueryParam("portfolio") String portfolioId,
            @QueryParam("ticker") String ticker
    ) {
        return transactionsService.getPortfolioTransactions(portfolioId, ticker);
    }

    @GET
    @Path("all") // No business case, only debugging.
    @Consumes(MediaType.APPLICATION_JSON)
    public List<TransactionEntry> getAllTransactions() {
        return transactionsDao.getTransactions();
    }


    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response saveTransactionHistory(
            @MultipartForm FileUploadBody body) {

        String fileName = body.fileName;
        InputStream file = body.file;

        if(fileName == null || fileName.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No filename was provided").build();
        }

        if(file == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No file was provided").build();
        }

        return transactionsService.postTransactionHistory(body);
    }
}
