package com.stockechoes.api.tickers;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/tickers")
public class TickerController {

    @Inject
    TickerRepository tickerRepository;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Ticker> getAllTickers() {
        return tickerRepository.listAll();
    }
}
