package com.safaricom.daraja_api.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimulateC2BTransactionResponse {
    @JsonProperty("OriginatorCoversationID")
    private String originatorConversionId;
    @JsonProperty("ConversationID")
    private String conversationId;
    @JsonProperty("ResponseDescription")
    private String responseDescription;
    @JsonProperty("ResponseCode")
    private String responseCode;
}
