package com.safaricom.daraja_api.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterUrlRequest {
    @JsonProperty("ValidationURL")
    private String validationUrl;
    @JsonProperty("ConfirmationURL")
    private String confirmationUrl;
    @JsonProperty("ResponseType")
    private String responseType;
    @JsonProperty("ShortCode")
    private String shortCode;
}
