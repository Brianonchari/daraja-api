package com.safaricom.daraja_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DarajaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DarajaApiApplication.class, args);
    }

    @Bean
    public OkHttpClient getHttpClient(){
        return new OkHttpClient();
    }

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }

}
