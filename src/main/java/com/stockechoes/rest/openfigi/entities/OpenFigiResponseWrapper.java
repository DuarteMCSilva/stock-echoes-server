package com.stockechoes.rest.openfigi.entities;

import com.stockechoes.services.business.isin.IsinRecord;
import lombok.Data;

import java.util.List;

@Data
public class OpenFigiResponseWrapper {

    private List<IsinRecord> data;
}
