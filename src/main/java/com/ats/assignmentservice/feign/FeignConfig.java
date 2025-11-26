package com.ats.assignmentservice.feign;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Client;
import feign.httpclient.ApacheHttpClient;

@Configuration
public class FeignConfig {

    @Bean
    public Client feignClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .disableCookieManagement()
                .build();

        return new ApacheHttpClient(httpClient);
    }
}
