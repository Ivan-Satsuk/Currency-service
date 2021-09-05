package com.alfabank.currencyservice;


import com.alfabank.currencyservice.models.GiphyRootDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${feign.GiphyAPI.name}", url = "${feign.GiphyAPI.url}")
public interface GiphyApiClient {

    @RequestMapping()
    public GiphyRootDTO getGif(@RequestParam(name = "api_key") String app_key,
                               @RequestParam(name = "tag") String tag);


}
