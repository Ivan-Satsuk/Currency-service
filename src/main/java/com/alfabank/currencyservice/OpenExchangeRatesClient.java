package com.alfabank.currencyservice;

import com.alfabank.currencyservice.models.CurrencyModelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "${feign.exchangeRates.name}", url = "${feign.exchangeRates.url}")
public interface OpenExchangeRatesClient {

    @RequestMapping("/historical/{date}.json")
    public CurrencyModelDTO getPreviousDayRate(@PathVariable(name = "date") String date,
                                               @RequestParam(name = "app_id") String app_id,
                                               @RequestParam(name = "base") String base,
                                               @RequestParam(name = "symbols") String symbols);

    @RequestMapping("/latest.json")
    public CurrencyModelDTO getLatestRate (@RequestParam(name = "app_id") String app_id,
                                           @RequestParam(name = "base") String base,
                                           @RequestParam(name = "symbols") String symbols);





}
