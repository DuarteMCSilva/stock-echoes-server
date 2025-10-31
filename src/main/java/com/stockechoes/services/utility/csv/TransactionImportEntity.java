package com.stockechoes.services.utility.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@NoArgsConstructor
public class TransactionImportEntity {

    @CsvBindByName(column = "ISIN")
    private String isin;

    @CsvDate("dd-MM-yyyy")
    @CsvBindByName(column = "Data")
    private LocalDate date;

    @CsvBindByName(column = "Quantidade")
    private int quantity;

    @CsvCustomBindByName(column = "Preços", converter = DecimalConverter.class)
    private BigDecimal unitPrice;

    @CsvCustomBindByName(column = "Total", converter = DecimalConverter.class)
    private BigDecimal totalPrice;

}
