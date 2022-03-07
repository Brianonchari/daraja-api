package com.safaricom.daraja_api.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SimulateC2BTransactionRequest {
    @JsonProperty("CommandID")
    private String commandId;
    @JsonProperty("Amount")
    private String amount;
    @JsonProperty("Msisdn")
    private String msisdn;
    @JsonProperty("BillRefNumber")
    private String billRefNumber;
    @JsonProperty("ShortCode")
    private String shortCode;
}
