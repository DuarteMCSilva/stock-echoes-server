package com.stockechoes.api.portfolio.holdings;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.SequencedCollection;

@ApplicationScoped
@Path("/holdings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
//@RolesAllowed({"user"}) // TODO: Reactivate when relevant
public class HoldingsResource {
    
    @Inject
    HoldingsService holdingsService;
    
    @GET
    public SequencedCollection<HoldingDto> getHoldings(
            @QueryParam("portfolio") Long portfolioId
    ) {
        return holdingsService.getCurrentHoldings(portfolioId);
    }
}
