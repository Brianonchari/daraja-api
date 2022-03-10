package com.safaricom.daraja_api.services;

import com.safaricom.daraja_api.dtos.requests.InternalB2CTransactionRequest;
import com.safaricom.daraja_api.dtos.requests.InternalTransactionStatusRequest;
import com.safaricom.daraja_api.dtos.requests.SimulateC2BTransactionRequest;
import com.safaricom.daraja_api.dtos.responses.*;

public interface DarajaApi {

    AccessTokenResponse getAccessToken();

    RegisterUrlResponse registerUrl();

    SimulateC2BTransactionResponse simulateC2BTransaction(SimulateC2BTransactionRequest simulateTransactionRequest);

    B2CTransactionResponse performB2cTransaction(InternalB2CTransactionRequest internalB2CTransactionRequest);

    TransactionStatusResponse getTransactionStatus(InternalTransactionStatusRequest internalTransactionStatusRequest);
}
