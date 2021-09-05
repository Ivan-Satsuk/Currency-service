package com.alfabank.currencyservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImagesDTO {
    @JsonProperty("fixed_height")
    public FixedHeightGifDTO fixed_height;
}
