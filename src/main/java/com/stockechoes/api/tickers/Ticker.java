package com.stockechoes.api.tickers;

import com.stockechoes.services.business.isin.IsinRecord;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name="ticker_table")
@Table(name="ticker_table")
@NoArgsConstructor
public class Ticker extends PanacheEntityBase {

    @Id
    private String isin;
    private String symbol;

    @Column(name = "security_type")
    private String securityType;

    @Column(name = "company_name")
    private String companyName;

    public Ticker(String isin) {
        this.isin = isin;
    }

    public Ticker(String isin, IsinRecord isinRecord) {
        this.isin = isin;
        this.symbol = isinRecord.getTicker();
        this.companyName = isinRecord.getName();
        this.securityType = isinRecord.getSecurityType();
    }

}
