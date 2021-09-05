package com.alfabank.currencyservice;

import com.alfabank.currencyservice.models.*;
import org.springframework.beans.factory.annotation.Value;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.*;

@Service
public class CurrencyService {
    @Value("${app.id.exchangeRates.client}")
    private String idExchangeRatesApi;
    @Value("${baseCurrency}")
    private String baseCurrency;
    @Value("${Giphy.API.key}")
    private String giphyApiKey;

    private GiphyApiClient giphyApiClient;
    private OpenExchangeRatesClient openExchangeRatesClient;

    @Autowired
    public void setGiphyApiClient(GiphyApiClient giphyApiClient) {
        this.giphyApiClient = giphyApiClient;
    }

    @Autowired
    public void setOpenExchangeRatesClient(OpenExchangeRatesClient openExchangeRatesClient) {
        this.openExchangeRatesClient = openExchangeRatesClient;
    }

    public String makeAnswerForRequest(String currencyCode) {

        //проверка того, что переданный код является кодом одной из валют
        Currency currency = Currency.getInstance(currencyCode.toUpperCase(Locale.ROOT));
        String rateCurrency = currency.getCurrencyCode();

        CurrencyModelDTO previousDaysRate = getPreviousDayRate(rateCurrency);
        CurrencyModelDTO latestRate = getLatestRate(rateCurrency);

        System.out.println(latestRate.getRates());
        System.out.println("-2: "+previousDaysRate);


        if ((latestRate.getCurrentCurrencyRate(rateCurrency).compareTo(previousDaysRate.getCurrentCurrencyRate(rateCurrency)) < 0))
            return getGifUrl("rich");
        else
            return getGifUrl("broke");
    }

    private CurrencyModelDTO getPreviousDayRate(String rateCurrency) {
        String yest = LocalDate.now(Clock.systemUTC()).minusDays(1).toString();
        CurrencyModelDTO test = openExchangeRatesClient.getPreviousDayRate(yest, idExchangeRatesApi, baseCurrency, rateCurrency);
        System.out.println("-1: "+test.getRates()+" "+yest);
        String previousDate = LocalDate.now(Clock.systemUTC()).minusDays(2).toString();
        System.out.println(previousDate+" -2");
        return openExchangeRatesClient.getPreviousDayRate(previousDate, idExchangeRatesApi, baseCurrency, rateCurrency);
    }

    private CurrencyModelDTO getLatestRate(String rateCurrency) {
        return openExchangeRatesClient.getLatestRate(idExchangeRatesApi, baseCurrency, rateCurrency);
    }

    private String getGifUrl(String tag) {
        GiphyRootDTO giphyRootDTO = giphyApiClient.getGif(giphyApiKey, "rich");
        DataDTO dataDTO = giphyRootDTO.getDataDTO();
        ImagesDTO images = dataDTO.getImages();
        FixedHeightGifDTO fixedHeightGifDTO = images.getFixed_height();
        return fixedHeightGifDTO.getUrl();
    }

}
