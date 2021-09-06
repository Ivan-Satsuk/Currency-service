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
        // check that the passed code is one of the currencies
        Currency currency = Currency.getInstance(currencyCode.toUpperCase(Locale.ROOT));
        String rateCurrency = currency.getCurrencyCode();

        CurrencyModelDTO previousDaysRate = getPreviousDayRate(rateCurrency);
        CurrencyModelDTO latestRate = getLatestRate(rateCurrency);

        if ((latestRate.getCurrentCurrencyRate(rateCurrency).compareTo(previousDaysRate.getCurrentCurrencyRate(rateCurrency)) < 0))
            return getGifUrl("rich");
        else
            return getGifUrl("broke");
    }

    private CurrencyModelDTO getPreviousDayRate(String rateCurrency) {
        String previousDate = LocalDate.now(Clock.systemUTC()).minusDays(1).toString();
        return openExchangeRatesClient.getPreviousDayRate(previousDate, idExchangeRatesApi, baseCurrency, rateCurrency);
    }

    private CurrencyModelDTO getLatestRate(String rateCurrency) {
        return openExchangeRatesClient.getLatestRate(idExchangeRatesApi, baseCurrency, rateCurrency);
    }

    private String getGifUrl(String tag) {
        GiphyRootDTO giphyRootDTO = giphyApiClient.getGif(giphyApiKey, tag);
        DataDTO dataDTO = giphyRootDTO.getDataDTO();
        ImagesDTO images = dataDTO.getImages();
        FixedHeightGifDTO fixedHeightGifDTO = images.getFixed_height();
        return fixedHeightGifDTO.getUrl();
    }

}
