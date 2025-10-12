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

    public boolean hasError() {
        return data == null || warning != null || error != null;
    }

    public String getMessage() {
        if(error != null) {
            return String.format("ERROR: %s%n", error);
        }
        if(warning != null) {
            return String.format("WARNING: %s%n", warning);
        }
        if(data == null) {
            return "INFO: NO DATA%n";
        }
        return "";
    }
}
