package com.safaricom.daraja_api.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReferenceItem {
    @JsonProperty("Value")
    private String value;
    @JsonProperty("Key")
    private String key;
}
