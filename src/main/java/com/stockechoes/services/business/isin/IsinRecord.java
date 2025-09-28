package com.stockechoes.services.business.isin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class IsinRecord {

    private String isin;
    private String ticker;
    private String name;
    private String securityType;

    @JsonProperty("exchCode")
    private String exchangeCode;
}
