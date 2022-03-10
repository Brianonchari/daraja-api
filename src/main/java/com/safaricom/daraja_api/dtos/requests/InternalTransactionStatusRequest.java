package com.safaricom.daraja_api.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InternalTransactionStatusRequest {
    @JsonProperty("TransactionID")
    private String transactionID;
}
