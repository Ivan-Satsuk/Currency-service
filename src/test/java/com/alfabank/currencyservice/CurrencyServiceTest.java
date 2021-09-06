package com.alfabank.currencyservice;

import com.alfabank.currencyservice.models.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@SpringBootTest
public class CurrencyServiceTest {
    @MockBean
    GiphyApiClient giphyApiClient;
    @MockBean
    OpenExchangeRatesClient openExchangeRatesClient;
    @Autowired
    CurrencyService currencyService;


    @ParameterizedTest
    @ValueSource(strings = {"74.00", "75.00", "76.00"})
    public void makeAnswerForRequestTest(String rate){
        String expectedResult="TestUrl";

        GiphyRootDTO giphyRootDTOTest = new GiphyRootDTO();
        giphyRootDTOTest.setDataDTO(new DataDTO());
        giphyRootDTOTest.getDataDTO().setImages(new ImagesDTO());
        giphyRootDTOTest.getDataDTO().getImages().setFixed_height(new FixedHeightGifDTO());
        giphyRootDTOTest.getDataDTO().getImages().getFixed_height().setUrl(expectedResult);;
        given(this.giphyApiClient.getGif(anyString(),anyString())).willReturn(giphyRootDTOTest);

        CurrencyModelDTO currencyModelDTOTest = new CurrencyModelDTO();
        HashMap<String,BigDecimal> testMap = new HashMap<>();
        testMap.put("RUB",new BigDecimal("75.00"));
        currencyModelDTOTest.setRates(testMap);
        given(openExchangeRatesClient.getLatestRate(anyString(),anyString(),anyString())).willReturn(currencyModelDTOTest);

        CurrencyModelDTO currencyModelDTO2Test = new CurrencyModelDTO();
        HashMap<String,BigDecimal> testMap2 = new HashMap<>();
        testMap2.put("RUB",new BigDecimal(rate));
        currencyModelDTO2Test.setRates(testMap2);
        given(openExchangeRatesClient.getPreviousDayRate(anyString(),anyString(),anyString(),anyString())).willReturn(currencyModelDTO2Test);

        String result = currencyService.makeAnswerForRequest("RUB");

        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {"RU", "USDD", " "})
    public void makeAnswerForRequestExpThrowingTest(String currency){
        assertThatIllegalArgumentException().isThrownBy(()->currencyService.makeAnswerForRequest(currency));
    }

}
