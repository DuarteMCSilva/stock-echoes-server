package com.stockechoes.rest.openfigi.entities;

import com.stockechoes.services.business.isin.IsinRecord;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class OpenFigiResponseWrapper {

    private List<IsinRecord> data;
    private String warning;
    private String error;
}
