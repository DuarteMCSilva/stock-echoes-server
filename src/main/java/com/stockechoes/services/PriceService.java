package com.stockechoes.services;

import com.stockechoes.domain.dao.PriceEntryDao;
import com.stockechoes.domain.dto.PriceEntryDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PriceService {

    @Inject
    PriceEntryDao priceEntryDao;

    public List<PriceEntryDto> getPricesByTicker(String symbol) {
        // TODO: Might need to get this from webscraper, if not in the database;
        return priceEntryDao.getPricesByTicker(symbol).stream().toList();
    }
}
