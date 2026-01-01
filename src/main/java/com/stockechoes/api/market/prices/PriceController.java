package com.stockechoes.api.market.prices;

import com.stockechoes.api.market.prices.dto.IntradayPriceForm;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("/price")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PriceController {

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

    @GET
    @Path("/web/eod")
    public Response getEodPricesForTickerList(IntradayPriceForm body) {
        var prices = priceService.getEodPricesForTickerList(body);
        return Response.ok().entity(prices).build();
    }
}
