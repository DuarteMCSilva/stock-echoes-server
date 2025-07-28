package com.stockechoes.resources.http;

import com.stockechoes.domain.dto.PriceEntryDto;
import com.stockechoes.services.PriceService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@ApplicationScoped
@Path("/price")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PriceResource {

    @Inject
    PriceService priceService;

    @GET
    @Path("{symbol}")
    public List<PriceEntryDto> getPricesByTicker(@PathParam("symbol") String symbol) {
        return priceService.getPricesByTicker(symbol);
    }

    @GET
    @Path("/web/{symbol}")
    public List<PriceEntryDto> getPricesByTickerFromWeb(@PathParam("symbol") String symbol) {
        return priceService.getPricesByTickerFromWeb(symbol);
    }
}
