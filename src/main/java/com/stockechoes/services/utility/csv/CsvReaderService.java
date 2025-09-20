package com.stockechoes.services.utility.csv;

import java.io.*;
import java.util.*;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CsvReaderService {

    public List<TransactionImportEntity> getListFromCsv(InputStream stream) {
        InputStreamReader streamReader = new InputStreamReader(stream);
        CsvToBean<TransactionImportEntity> csvToBean = new CsvToBeanBuilder<TransactionImportEntity>(streamReader)
                .withType(TransactionImportEntity.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        return csvToBean.parse();
    }
}
