package com.stockechoes.api.portfolio.portfolios;

import com.stockechoes.api.auth.AuthContext;
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
    AuthContext authContext;

    @Inject
    PortfolioRepository portfolioRepository;

    @Inject
    PortfolioService portfolioService;

    @GET
    public List<Portfolio> getAccountPortfolios() {
        return portfolioService.getPortfoliosByAccount(authContext.getAccountId());
    }

    @POST
    @Transactional
    @Path("/create")
    public Response postPortfolio(@QueryParam("name") String name) {
        Portfolio portfolio = new Portfolio(name);
        portfolio.setAccountId(authContext.getAccountId());

        portfolioService.savePortfolioWithoutDuplicates(portfolio);

        return Response.status(Response.Status.CREATED).build();
    }
}
