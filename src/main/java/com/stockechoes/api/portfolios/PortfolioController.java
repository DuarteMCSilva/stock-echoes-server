package com.stockechoes.api.portfolios;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@ApplicationScoped
@Path("/portfolio")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SuppressWarnings("unused")
public class PortfolioController {

    @Inject
    PortfolioRepository portfolioRepository;

    @GET
    public List<Portfolio> getAll() {
        return portfolioRepository.listAll();
    }

    @GET
    @Path("{id}")
    public Portfolio getPortfolioById(@PathParam("id") Long id) {
        return portfolioRepository.findById(id);
    }

    @POST
    @Transactional
    @Path("/create")
    public Long postPortfolio(@QueryParam("name") String name) {
        //TODO: make with body, and more params.
        return portfolioRepository.createPortfolio(name);
    }
}
