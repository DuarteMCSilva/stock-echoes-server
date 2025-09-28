package com.stockechoes.rest.openfigi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockechoes.rest.openfigi.entities.OpenFigiRequestEntity;
import com.stockechoes.rest.openfigi.entities.OpenFigiResponseWrapper;
import com.stockechoes.services.business.isin.IsinRecord;
import com.stockechoes.rest.openfigi.exceptions.FigiErrorException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class OpenFigiService {

    @Inject
    @RestClient
    OpenFigiClient openFigiClient;

    public IsinRecord fetchIsinMap(String isin) throws FigiErrorException {
        OpenFigiRequestEntity params = new OpenFigiRequestEntity("ID_ISIN", isin);

        String response = openFigiClient.fetchTickerByIsin(List.of(params));

        List<OpenFigiResponseWrapper> wrappers;
        try {
            wrappers = mapToEntityOrThrow(response);
        } catch (JsonProcessingException e) {
            throw new FigiErrorException(e, params.toString());
        }

        IsinRecord stock = wrappers.getFirst().getData().getFirst();
        // TODO: refine, I want the correct one, not necessarily the first.
        stock.setIsin(isin);
        return stock;
    }

    private List<OpenFigiResponseWrapper> mapToEntityOrThrow(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(
                response,
                new TypeReference<List<OpenFigiResponseWrapper>>() {
                });
    }
}
