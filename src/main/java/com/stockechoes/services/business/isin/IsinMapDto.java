package com.stockechoes.services.business.isin;

import lombok.Data;

import java.util.HashMap;

@Data
public class IsinMapDto {

    private HashMap<String, String> stocks;

    private HashMap<String, String> etfs;

    public String getTicker(String name) {
        String ticker = stocks.get(name);
        if(ticker != null) {
            return ticker;
        }
        return etfs.get(name);
    }
}
