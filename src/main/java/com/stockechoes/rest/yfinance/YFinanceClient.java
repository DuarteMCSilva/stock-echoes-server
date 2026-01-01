package com.stockechoes.rest.yfinance;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "yfinance")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface YFinanceClient {

    @GET
    @Path("/last-prices")
    String fetchEodPrices(@QueryParam("tickers") String entities);

    @GET
    @Path("/historical-prices")
    String fetchHistoricalPrices(
            @QueryParam("ticker") String ticker,
            @QueryParam("p") String period
    );
}
