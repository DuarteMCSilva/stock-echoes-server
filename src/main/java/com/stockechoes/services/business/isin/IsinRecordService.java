package com.stockechoes.services.business.isin;

import com.stockechoes.rest.openfigi.OpenFigiService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class IsinRecordService {

    @Inject
    OpenFigiService openFigiService;

    public IsinRecord fetchCompanyByIsin(String isin) {
        return openFigiService.fetchIsinMap(isin);
    }
}

