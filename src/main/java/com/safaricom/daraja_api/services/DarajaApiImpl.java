package com.safaricom.daraja_api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safaricom.daraja_api.config.MpesaConfiguration;
import com.safaricom.daraja_api.dtos.requests.*;
import com.safaricom.daraja_api.dtos.responses.*;
import com.safaricom.daraja_api.utils.HelperUtility;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

import static com.safaricom.daraja_api.utils.Constants.*;

@Service
@Slf4j
public class DarajaApiImpl implements DarajaApi {
    private final MpesaConfiguration mpesaConfiguration;
    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    public DarajaApiImpl(MpesaConfiguration mpesaConfiguration, OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        this.mpesaConfiguration = mpesaConfiguration;
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
    }

    // This method returns Daraja API access response
    @Override
    public AccessTokenResponse getAccessToken() {
        //Get a base64 encoding using the consumer key and secret key
        String encodedCredentials = HelperUtility.toBase64String(String.format("%s:%s", mpesaConfiguration.getConsumerKey(),
                mpesaConfiguration.getConsumerSecret()));

        Request request = new Request.Builder()
                .url(String.format("%s?grant_type=%s", mpesaConfiguration.getOauthEndpoint(), mpesaConfiguration.getGrantType()))
                .get()
                .addHeader(AUTHORIZATION_HEADER_STRING, String.format("%s %s", BASIC_AUTH_STRING, encodedCredentials))
                .addHeader(CACHE_CONTROL_HEADER, CACHE_CONTROL_HEADER_VALUE)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            assert response.body() != null;

            // Deserialize response body to Java object
            return objectMapper.readValue(response.body().string(), AccessTokenResponse.class);
        } catch (IOException e) {
            log.error(String.format("Could not get access token. -> %s", e.getLocalizedMessage()));
            return null;
        }
    }


    @Override
    public RegisterUrlResponse registerUrl() {
        AccessTokenResponse accessTokenResponse = getAccessToken();
        RegisterUrlRequest registerUrlRequest = new RegisterUrlRequest();
        registerUrlRequest.setValidationUrl(mpesaConfiguration.getValidationURL());
        registerUrlRequest.setConfirmationUrl(mpesaConfiguration.getConfirmationURL());
        registerUrlRequest.setResponseType(mpesaConfiguration.getResponseType());
        registerUrlRequest.setShortCode(mpesaConfiguration.getShortCode());

        RequestBody requestBody = RequestBody.create(JSON_MEDIA_TYPE,
                Objects.requireNonNull(HelperUtility.toJson(registerUrlRequest)));

        Request request = new Request.Builder()
                .url(mpesaConfiguration.getRegisterUrlEndpoint())
                .post(requestBody)
                .addHeader(
                        AUTHORIZATION_HEADER_STRING,
                        String.format("%s %s", BEARER_AUTH_STRING, accessTokenResponse.getAccessToken())
                ).build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            assert response.body() != null;
            //Deserialize response body to 'RegisterUrlResponse' Java object
            return objectMapper.readValue(response.body().string(), RegisterUrlResponse.class);
        } catch (IOException e) {
            log.error(String.format("Could not register url >> %s", e.getLocalizedMessage()));
            return null;
        }
    }

    @Override
    public SimulateC2BTransactionResponse simulateC2BTransaction(SimulateC2BTransactionRequest simulateTransactionRequest) {
        AccessTokenResponse accessTokenResponse = getAccessToken();
        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE,
                Objects.requireNonNull(HelperUtility.toJson(simulateTransactionRequest)));

        Request request = new Request.Builder()
                .url(mpesaConfiguration.getSimulateTransactionEndpoint())
                .post(body)
                .addHeader(AUTHORIZATION_HEADER_STRING, String.format("%s %s", BEARER_AUTH_STRING, accessTokenResponse.getAccessToken()))
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            assert response.body() != null;

            return objectMapper.readValue(response.body().string(), SimulateC2BTransactionResponse.class);
        } catch (IOException e) {
            log.error(String.format("Could not simulate C2B transaction -> %s", e.getLocalizedMessage()));
            return null;
        }
    }

    @Override
    public B2CTransactionResponse performB2cTransaction(InternalB2CTransactionRequest internalB2CTransactionRequest) {
        AccessTokenResponse accessTokenResponse = getAccessToken();
        log.info("===Access Token ===");
        log.info(String.format("%s", accessTokenResponse.getAccessToken()));
        B2CTransactionRequest b2CTransactionRequest = new B2CTransactionRequest();
        b2CTransactionRequest.setCommandID(internalB2CTransactionRequest.getCommandID());
        b2CTransactionRequest.setAmount(internalB2CTransactionRequest.getAmount());
        b2CTransactionRequest.setPartyB(internalB2CTransactionRequest.getPartyB());
        b2CTransactionRequest.setRemarks(internalB2CTransactionRequest.getRemarks());
        b2CTransactionRequest.setOccassion(internalB2CTransactionRequest.getOccassion());

        b2CTransactionRequest.setSecurityCredential(HelperUtility.getSecurityCredentials(mpesaConfiguration.getB2cInitiatorPassword()));
        log.info(String.format("Security Credentials:  -->%s", b2CTransactionRequest.getSecurityCredential()));
        b2CTransactionRequest.setResultURL(mpesaConfiguration.getB2cResultUrl());
        b2CTransactionRequest.setQueueTimeOutURL(mpesaConfiguration.getB2cQueueTimeoutUrl());
        b2CTransactionRequest.setInitiatorName(mpesaConfiguration.getB2cInitiatorName());
        b2CTransactionRequest.setPartyA(mpesaConfiguration.getShortCode());

        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE,
                Objects.requireNonNull(HelperUtility.toJson(b2CTransactionRequest)));
        Request request = new Request.Builder()
                .url(mpesaConfiguration.getB2cTransactionEndpoint())
                .post(body)
                .addHeader(AUTHORIZATION_HEADER_STRING,
                        String.format("%s %s", BEARER_AUTH_STRING, accessTokenResponse.getAccessToken()))
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            assert response.body() != null;

            return objectMapper.readValue(response.body().string(), B2CTransactionResponse.class);
        } catch (IOException e) {
            log.error(String.format("Could not perfom B2C Transaction,>>> %s", e.getLocalizedMessage()));
            return null;
        }
    }

    @Override
    public TransactionStatusResponse getTransactionStatus(
            InternalTransactionStatusRequest internalTransactionStatusRequest
    ) {
        AccessTokenResponse accessTokenResponse = getAccessToken();
        log.info(String.format("ACCESS TOKEN --> %s", accessTokenResponse));
        TransactionStatusRequest transactionStatusRequest = new TransactionStatusRequest();
        transactionStatusRequest.setInitiator(mpesaConfiguration.getB2cInitiatorName());
        transactionStatusRequest.setSecurityCredential(
                HelperUtility.getSecurityCredentials(mpesaConfiguration.getB2cInitiatorPassword())
        );
        transactionStatusRequest.setCommandID(TRANSACTION_STATUS_QUERY_COMMAND);
        transactionStatusRequest.setPartyA(mpesaConfiguration.getShortCode());
        transactionStatusRequest.setIdentifierType(SHORT_CODE_IDENTIFIER);
        transactionStatusRequest.setResultURL(mpesaConfiguration.getB2cResultUrl());
        transactionStatusRequest.setQueueTimeOutURL(mpesaConfiguration.getB2cQueueTimeoutUrl());
        transactionStatusRequest.setRemarks("Remarks");
        transactionStatusRequest.setOccasion("Disbursement");


        RequestBody body = RequestBody.create(
                JSON_MEDIA_TYPE,
                Objects.requireNonNull(HelperUtility.toJson(transactionStatusRequest))
        );

        Request request = new Request.Builder()
                .url(mpesaConfiguration.getTransactionResultUrl())
                .post(body)
                .addHeader(AUTHORIZATION_HEADER_STRING,
                        String.format("%s %s", BEARER_AUTH_STRING, accessTokenResponse.getAccessToken()))
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            assert response.body() != null;
            //Deserialize to Java object;
            return objectMapper.readValue(response.body().toString(), TransactionStatusResponse.class);
        } catch (IOException e) {
            log.error(String.format("Unable to get transaction status, -> %s",e.getLocalizedMessage()));
            return null;
        }
    }
}
