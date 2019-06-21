
package com.aimlesshammer.pocpapi.client;

import java.util.List;
import com.aimlesshammer.pocpapi.model.CurrentAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import static org.springframework.http.HttpMethod.GET;

@Component
public class CurrentAccountSAPI {

    private final String customerIdKey = "{CUSTOMER_ID}";
    private RestTemplate restTemplate;
    @Value("${sapi.currentAccount.url}") private String urlTemplate;

    public CurrentAccountSAPI(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<CurrentAccount> currentAccounts(String customerId) {
        String urlString = urlTemplate.replace(customerIdKey, customerId);
        ResponseEntity<List<CurrentAccount>> entity = restTemplate.exchange(urlString, GET, null, new ParameterizedTypeReference<List<CurrentAccount>>() {});
        List<CurrentAccount> currentAccounts = entity.getBody();
        return currentAccounts;
    }

}
