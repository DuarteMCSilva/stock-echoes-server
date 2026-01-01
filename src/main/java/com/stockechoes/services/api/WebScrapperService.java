package com.stockechoes.services.api;

import com.stockechoes.api.market.prices.PriceEntryDto;
import com.stockechoes.api.market.prices.dto.IntradayPriceDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

import static com.stockechoes.services.utility.JsonUtils.listFromJson;

@ApplicationScoped
public class WebScrapperService {

    @ConfigProperty(name = "stock-echoes.scrapper.url")
    String webScrapperUrl;

    public List<PriceEntryDto> fetchPriceHistory(String ticker, String period) {
        String url = webScrapperUrl + "/historical-prices" +
                "?ticker=" + ticker + "&p=" + period;
        String responseBody = executeAndCatchRequest(url);

        return listFromJson(responseBody, PriceEntryDto.class);
    }

    public List<IntradayPriceDto> fetchEodPrices(List<String> tickers) {
        String url = webScrapperUrl + "/last-prices" +
                "?tickers=" + String.join(",", tickers);
        String responseBody = executeAndCatchRequest(url);

        return listFromJson(responseBody, IntradayPriceDto.class);
    }

    private String executeAndCatchRequest(String url) {
        Client client = ClientBuilder.newClient();

        WebTarget target = client.target(url);
        Response response;

        try {
            response = target.request(MediaType.APPLICATION_JSON).get();
        } catch (Exception e) {
            client.close();
            throw new RuntimeException("No response from Web-scrapping service." );
        }

        if (response.getStatus() != 200) {
            client.close();
            throw new RuntimeException("Failed to fetch data: " + response.getStatus());
        }

        String responseBody = response.readEntity(String.class);
        client.close();
        return responseBody;
    }

}
