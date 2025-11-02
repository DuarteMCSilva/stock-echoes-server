package com.stockechoes.api.portfolio.portfolios;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("/portfolio")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SuppressWarnings("unused")
public class PortfolioController {

    @Inject
    PortfolioRepository portfolioRepository;

    @Inject
    PortfolioService portfolioService;

    @GET
    public Response getAccountPortfolios() {
        List<Portfolio> portfolioList = portfolioService.getAccountPortfolios();
        return Response.status(Response.Status.OK).entity(portfolioList).build();
    }

    @POST
    @Transactional
    @Path("/create")
    public Response postPortfolio(@QueryParam("name") String name) {
        Portfolio portfolio = new Portfolio(name);

        portfolioService.savePortfolioWithoutDuplicates(portfolio);

        return Response.status(Response.Status.CREATED).build();
    }
}
