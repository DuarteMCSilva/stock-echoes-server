package com.stockechoes.rest.openfigi;

import com.stockechoes.rest.openfigi.entities.OpenFigiRequestEntity;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(baseUri = "https://api.openfigi.com/v3/mapping")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface OpenFigiClient {

    @POST
    String fetchTickerByIsin(List<OpenFigiRequestEntity> entities);

}
