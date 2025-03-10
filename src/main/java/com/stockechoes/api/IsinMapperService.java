package com.stockechoes.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockechoes.api.dto.IsinMapDto;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


@ApplicationScoped
public class IsinMapperService {

    @ConfigProperty(name = "stock-echoes.isin.url")
    String isinUrl;

    public IsinMapDto fetchIsinMap() {
        return  executeAndCatchRequest(isinUrl);
    }

    private IsinMapDto executeAndCatchRequest(String url) {
        InputStream response = getClass().getClassLoader().getResourceAsStream(url);

        if (response == null) {
            throw new RuntimeException("ISIN file not found!");
        }

        String json = new Scanner(response, StandardCharsets.UTF_8).useDelimiter("\\A").next();
        return jsonToMap(json);
    }

    private IsinMapDto jsonToMap(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, IsinMapDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
