package com.alfabank.currencyservice.models;


import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class DataDTO {
    private String type;
    private String id;
    private String url;
    @JsonProperty("images")
    private ImagesDTO images;
}

