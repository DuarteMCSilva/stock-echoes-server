package com.stockechoes.services.business.isin;

import com.stockechoes.rest.openfigi.OpenFigiService;
import com.stockechoes.rest.openfigi.entities.OpenFigiRequestEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class IsinRecordService {

    @Inject
    OpenFigiService openFigiService;

    public List<IsinRecord> fetchCompanyByIsinList(List<String> isinList) {
        List<OpenFigiRequestEntity> requestEntities = isinList.stream()
                .map((isin) -> new OpenFigiRequestEntity("ID_ISIN", isin))
                .toList();

        return openFigiService.fetchIsinMap(requestEntities);
    }
}

