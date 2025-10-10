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
import java.util.Objects;
import java.util.stream.IntStream;

@ApplicationScoped
public class OpenFigiService {

    @Inject
    @RestClient
    OpenFigiClient openFigiClient;

    private List<OpenFigiResponseWrapper> mapToEntityOrThrow(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, new TypeReference<>() {});
    }

    public List<IsinRecord> fetchIsinMap(List<OpenFigiRequestEntity> params) throws FigiErrorException {

        String response = openFigiClient.fetchTickerByIsin(params);

        List<OpenFigiResponseWrapper> wrappers;
        try {
            wrappers = mapToEntityOrThrow(response);
        } catch (JsonProcessingException e) {
            throw new FigiErrorException(e, params.toString());
        }

        return IntStream.range(0, wrappers.size())
                .mapToObj(i -> {
                    String isin = params.get(i).getIdValue();
                    return handleEmptyRecords(wrappers.get(i), isin);
                })
                .filter(Objects::nonNull).toList();
    }

    private IsinRecord handleEmptyRecords(OpenFigiResponseWrapper wrapper, String isin) {
        String error = wrapper.getError();
        String warning = wrapper.getWarning();
        List<IsinRecord> data = wrapper.getData();

        if(data == null || error != null || warning != null) {
            System.out.printf("Could not find data for ISIN %s%n", isin);
            if(error != null) System.out.printf("Error: %s%n", error);
            if(warning != null) System.out.printf("Warning: %s%n", warning);
            return null;
        }

        IsinRecord record = data.getFirst(); // TODO: not exactly first.
        record.setIsin(isin);
        return record;
    }
}
