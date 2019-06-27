
package com.aimlesshammer.pocpapi.client;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

public class SapiClient<T> {

    private final WebClient webClient;
    private final String urlTemplate;
    private final Integer retryCount;
    private final Class<T[]> responseClass;

    public SapiClient(
        WebClient webClient,
        String urlTemplate,
        Integer retryCount,
        Class<T[]> responseClass
    ) {
        this.webClient = webClient;
        this.urlTemplate = urlTemplate;
        this.retryCount = retryCount;
        this.responseClass = responseClass;
    }

    public List<T> get(String customerId) {
        final String urlString = urlTemplate.replace("{CUSTOMER_ID}", customerId);
        return webClient
            .get()
            .uri(urlString)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(responseClass)
            .map(Arrays::asList)
            .retry(retryCount)
            .block();
    }

}
