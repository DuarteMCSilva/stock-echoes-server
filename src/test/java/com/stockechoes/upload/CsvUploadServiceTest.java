package com.stockechoes.upload;

import com.stockechoes.services.utility.csv.CsvReaderService;
import com.stockechoes.services.utility.csv.TransactionImportEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CsvUploadServiceTest {

    @InjectMocks
    CsvReaderService csvReaderService;

    @Test
    @DisplayName("getAll should return all users mapped to WorkationDto")
    void upload() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/data/transactions.csv");
        assertNotNull(inputStream, "CSV file not found in resources!");
        List<TransactionImportEntity> transactions = csvReaderService.getListFromCsv(inputStream);

        assertEquals(18, transactions.size());
    }
}
