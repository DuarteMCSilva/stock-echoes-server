package com.stockechoes.rest.openfigi.entities;

import lombok.Data;

@Data
public class OpenFigiRequestEntity {

    private String idType;
    private String idValue;

    public OpenFigiRequestEntity(String idType, String idValue) {
        this.idType = idType;
        this.idValue = idValue;
    }
}
