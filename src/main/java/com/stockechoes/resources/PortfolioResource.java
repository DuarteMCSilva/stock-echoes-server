package com.stockechoes.resources;

import com.stockechoes.domain.dao.PortfolioDao;
import com.stockechoes.domain.model.Portfolio;
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
public class PortfolioResource {

    @Inject
    PortfolioDao portfolioDao;

    @GET
    public List<Portfolio> getAll() {
        return Portfolio.listAll();
    }

    @GET
    @Path("{id}")
    public Portfolio getPortfolioById(@PathParam("id") Long id) {
        return Portfolio.findById(id);
    }

    @POST
    @Path("create")
    @Transactional
    public void createPortfolio() {
        portfolioDao.createPortfolio("test-portfolio");
    }
}
