
package com.aimlesshammer.pocpapi.client;

import java.util.List;
import com.aimlesshammer.pocpapi.model.CreditAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import static org.springframework.http.HttpMethod.GET;

@Component
public class CreditAccountSAPI {

    private final String customerIdKey = "{CUSTOMER_ID}";
    private RestTemplate restTemplate;
    @Value("${sapi.creditCard.url}") private String urlTemplate;

    public CreditAccountSAPI(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<CreditAccount> creditCards(String customerId) {
        String urlString = urlTemplate.replace(customerIdKey, customerId);
        ResponseEntity<List<CreditAccount>> entity = restTemplate.exchange(urlString, GET, null, new ParameterizedTypeReference<List<CreditAccount>>() {});
        List<CreditAccount> creditAccounts = entity.getBody();
        return creditAccounts;
    }

}
