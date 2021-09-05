package com.alfabank.currencyservice.models;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.TreeMap;

@Data
public class CurrencyModelDTO {
    private String base;
    private HashMap<String, BigDecimal> rates;
    private BigDecimal rate;


    public BigDecimal getCurrentCurrencyRate(String rateCurrency) {
         return rates.get(rateCurrency);
    }

    @Override
    public String toString() {
        return "CurrencyModel"  + rates;
    }
}
