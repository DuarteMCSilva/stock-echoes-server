package com.stockechoes.services.business.isin;

import com.stockechoes.rest.openfigi.OpenFigiService;
import com.stockechoes.rest.openfigi.entities.OpenFigiRequestEntity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class IsinRecordService {

    @Inject
    OpenFigiService openFigiService;

    public IsinRecord fetchCompanyByIsin(String isin) {
        return openFigiService.fetchIsinMap(isin);
    }

    public List<IsinRecord> fetchCompanyByIsin(List<String> isins) {
        List<OpenFigiRequestEntity> requestEntities = isins.stream()
                .map((isin) -> new OpenFigiRequestEntity("ID_ISIN", isin))
                .toList();

        return openFigiService.fetchIsinMap(requestEntities);
    }
}

