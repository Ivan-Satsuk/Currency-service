package com.alfabank.currencyservice.models;


import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class GiphyRootDTO {
    @JsonProperty("data")
    private DataDTO dataDTO;
}
