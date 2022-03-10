package com.safaricom.daraja_api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safaricom.daraja_api.dtos.requests.InternalB2CTransactionRequest;
import com.safaricom.daraja_api.dtos.requests.SimulateC2BTransactionRequest;
import com.safaricom.daraja_api.dtos.responses.*;
import com.safaricom.daraja_api.services.DarajaApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mobile-money")
@Slf4j
public class MpesaController {
    private final DarajaApi darajaApi;
    private final AcknowledgeResponse acknowledgeResponse;
    private final ObjectMapper objectMapper;


    public MpesaController(DarajaApi darajaApi, AcknowledgeResponse acknowledgeResponse, ObjectMapper objectMapper) {
        this.darajaApi = darajaApi;
        this.acknowledgeResponse = acknowledgeResponse;
        this.objectMapper = objectMapper;
    }

    @GetMapping(path = "/token", produces = "application/json")
    public ResponseEntity<AccessTokenResponse> getAccessToken() {
        return ResponseEntity.ok(darajaApi.getAccessToken());
    }

    @GetMapping(path = "/register-url", produces = "application/json")
    public ResponseEntity<RegisterUrlResponse> registerUrl() {
        return ResponseEntity.ok(darajaApi.registerUrl());
    }

    @PostMapping(path = "/validation", produces = "application/json")
    public ResponseEntity<AcknowledgeResponse> mpesaValidation(
            @RequestBody TransactionResult transactionResult) {
        return ResponseEntity.ok(acknowledgeResponse);
    }

    @PostMapping(path = "/simulate-c2b", produces = "application/json")
    public ResponseEntity<SimulateC2BTransactionResponse> simulateB2CTransaction(
            @RequestBody SimulateC2BTransactionRequest simulateTransactionRequest
    ) {
        return ResponseEntity.ok(darajaApi.simulateC2BTransaction(simulateTransactionRequest));
    }

    //b2c callback url
    @PostMapping(path = "/b2c-trnsaction-result", produces = "application/json")
    public ResponseEntity<AcknowledgeResponse> b2cTransactionAsyncResult(
            @RequestBody B2CTransactionAsyncResponse b2CTransactionAsyncResponse
    ) throws JsonProcessingException {
        log.info("B2C Transaction Response");
        log.info(objectMapper.writeValueAsString(b2CTransactionAsyncResponse));
        return ResponseEntity.ok(acknowledgeResponse);
    }

    @PostMapping(path = "/b2c-queue-timeout", produces = "application/json")
    public ResponseEntity<AcknowledgeResponse> queueTimeout(@RequestBody Object object) {
        return ResponseEntity.ok(acknowledgeResponse);
    }

    @PostMapping(path = "b2c-transaction", produces = "application/json")
    public ResponseEntity<B2CTransactionResponse> performB2CTransaction(
            @RequestBody InternalB2CTransactionRequest internalB2CTransactionRequest
    ) {
        return ResponseEntity.ok(darajaApi.performB2cTransaction(internalB2CTransactionRequest));
    }
}
