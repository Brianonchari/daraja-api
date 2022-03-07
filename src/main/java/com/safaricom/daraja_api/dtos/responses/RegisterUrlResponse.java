package com.safaricom.daraja_api.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterUrlResponse {
    @JsonProperty("ResponseCode")
    private String conversationId;
    @JsonProperty("ResponseDescription")
    private String responseDescription;
    @JsonProperty("OriginatorCoversationID")
    private String originatorConverstionId;
}
