package com.safaricom.daraja_api.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class B2CTransactionAsyncResponse {
    @JsonProperty("Result")
    private Result result;
}
