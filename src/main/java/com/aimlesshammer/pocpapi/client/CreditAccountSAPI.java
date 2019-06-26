
package com.aimlesshammer.pocpapi.client;

import java.util.Arrays;
import java.util.List;
import com.aimlesshammer.pocpapi.model.CreditAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CreditAccountSAPI {

    private final WebClient webClient;
    private final String urlTemplate;

    public CreditAccountSAPI(WebClient webClient, @Value("${sapi.creditCard.url}") String urlTemplate) {
        this.webClient = webClient;
        this.urlTemplate = urlTemplate;
    }

    public List<CreditAccount> creditCards(String customerId) {
        final String urlString = urlTemplate.replace("{CUSTOMER_ID}", customerId);
        return webClient
            .get()
            .uri(urlString)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(CreditAccount[].class)
            .map(Arrays::asList)
            .block();
    }

}
