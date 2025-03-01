package com.stockechoes.resources;

import com.stockechoes.domain.model.Portfolio;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@ApplicationScoped
@Path("/portfolio")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PortfolioResource {

    @GET
    public List<Portfolio> getAll() {
        return Portfolio.listAll();
    }

    @GET
    @Path("{id}")
    public Portfolio getPortfolioById(@PathParam("id") Long id) {
        return Portfolio.findById(id);
    }
}
