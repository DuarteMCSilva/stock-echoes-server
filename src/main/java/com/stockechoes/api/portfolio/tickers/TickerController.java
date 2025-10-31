package com.stockechoes.api.portfolio.tickers;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/tickers")
public class TickerController {

    @Inject
    TickerRepository tickerRepository;

    @GET
    public List<Ticker> getAllTickers() {
        return tickerRepository.listAll();
    }
}
