package com.alfabank.currencyservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyController {
    CurrencyService currencyService;

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/{currencyCode}")
    public String chekRate(@PathVariable(name = "currencyCode") String currencyCode){
        return currencyService.makeAnswerForRequest(currencyCode);

    }
}
