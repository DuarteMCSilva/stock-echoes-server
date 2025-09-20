package com.stockechoes.services.utility.csv;

import com.opencsv.bean.AbstractBeanField;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DecimalConverter extends AbstractBeanField<BigDecimal, String> {

    @Override
    protected BigDecimal convert(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.GERMANY); // , as decimal separator
            DecimalFormat df = new DecimalFormat("#,##0.####", symbols);
            df.setParseBigDecimal(true);
            return (BigDecimal) df.parse(value);
        } catch (Exception e) {
            throw new RuntimeException("Invalid number format: " + value, e);
        }
    }
}

