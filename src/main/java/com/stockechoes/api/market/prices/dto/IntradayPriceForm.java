package com.stockechoes.api.market.prices.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class IntradayPriceForm {

    private List<String> tickers;
}
