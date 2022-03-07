package com.safaricom.daraja_api.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterUrlResponse {
    @JsonProperty("OriginatorConverstionID")
    private String originatorConverstionId;
    @JsonProperty("ConversationID")
    private String conversationId;
    @JsonProperty("ResponseDescription")
    private String responseDescription;
}
