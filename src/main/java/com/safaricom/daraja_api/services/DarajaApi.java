package com.safaricom.daraja_api.services;

import com.safaricom.daraja_api.dtos.requests.RegisterUrlRequest;
import com.safaricom.daraja_api.dtos.responses.AccessTokenResponse;
import com.safaricom.daraja_api.dtos.responses.RegisterUrlResponse;

public interface DarajaApi {

    AccessTokenResponse getAccessToken();

    RegisterUrlResponse registerUrl();
}
