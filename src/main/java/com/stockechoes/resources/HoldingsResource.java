package com.stockechoes.resources;

import com.stockechoes.domain.dto.HoldingDto;
import com.stockechoes.services.HoldingsService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.SequencedCollection;

@ApplicationScoped
@Path("/holdings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"user"})
public class HoldingsResource {
    
    @Inject
    HoldingsService holdingsService;
    
    @GET
    public SequencedCollection<HoldingDto> getHoldings(
            @QueryParam("portfolio") String portfolioId
    ) {
        return holdingsService.getCurrentHoldings(portfolioId);
    }
}
