package com.stockechoes.api.rest.openfigi;

import com.stockechoes.rest.openfigi.OpenFigiService;
import com.stockechoes.services.business.isin.IsinRecord;
import com.stockechoes.rest.openfigi.exceptions.FigiErrorException;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

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
    OpenFigiService openFigiService;

    @Test
    void isinDataLookup() {
        String isin = "US0605051046";

        IsinRecord stock = openFigiService.fetchIsinMap(isin);

        assertEquals("BAC", stock.getTicker());
        assertEquals("BANK OF AMERICA CORP", stock.getName());
        assertEquals(isin, stock.getIsin());
        assertEquals("US", stock.getExchangeCode());
        assertEquals("Common Stock", stock.getSecurityType());
    }

    @Test
    void isinDataLookup_withDummyIsin() {
        String isin = "MOCK_NOT_FOUND_ISIN";

        assertThrows(FigiErrorException.class,
                () -> openFigiService.fetchIsinMap(isin));
    }

}
