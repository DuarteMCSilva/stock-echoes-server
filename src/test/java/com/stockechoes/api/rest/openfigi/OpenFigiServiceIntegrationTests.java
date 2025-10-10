package com.stockechoes.api.rest.openfigi;

import com.stockechoes.rest.openfigi.OpenFigiService;
import com.stockechoes.services.business.isin.IsinRecord;
import com.stockechoes.rest.openfigi.exceptions.FigiErrorException;
import com.stockechoes.services.business.isin.IsinRecordService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * IMPORTANT NOTE:
 * Tests the compatibility with the external ISIN API.
 * Requires internet connection and, possibly, valid API credentials.
 * This is an integration test and may fail if the API is down or data changes.
 */
@QuarkusTest
@Tag("integration-openfigi")
public class OpenFigiServiceIntegrationTests {

    @Inject
    IsinRecordService isinRecordService;

    @Test
    @Disabled
    void isinDataLookup_withDummyIsin() {
        String isin = "MOCK_NOT_FOUND_ISIN";

        assertThrows(FigiErrorException.class,
                () -> isinRecordService.fetchCompanyByIsinList(List.of(isin)));
    }

    @Test
    @Disabled
    void isinDataLookup_Multi() {
        List<IsinRecord> list = isinRecordService.fetchCompanyByIsinList(List.of("US0605051046","US0378331005"));

        assertEquals(list.getFirst().getIsin(), "US0605051046");
        assertEquals(list.getFirst().getTicker(), "BAC");
        assertEquals(list.get(1).getIsin(), "US0378331005");
        assertEquals(list.get(1).getTicker(), "AAPL");
    }
}
