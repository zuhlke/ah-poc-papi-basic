
package com.aimlesshammer.pocpapi.client;

import java.util.List;
import com.aimlesshammer.pocpapi.model.CurrentAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import static org.springframework.http.HttpMethod.GET;

@Service
public class CurrentAccountSAPI {

    private final RestTemplate restTemplate;
    private final String urlTemplate;

    public CurrentAccountSAPI(RestTemplateBuilder restTemplateBuilder, @Value("${sapi.currentAccount.url}") String urlTemplate) {
        this.restTemplate = restTemplateBuilder.build();
        this.urlTemplate = urlTemplate;
    }

    public List<CurrentAccount> currentAccounts(String customerId) {
        String urlString = urlTemplate.replace("{CUSTOMER_ID}", customerId);
        ResponseEntity<List<CurrentAccount>> entity = restTemplate.exchange(
            urlString,
            GET,
            null,
            new ParameterizedTypeReference<List<CurrentAccount>>() {}
        );
        return entity.getBody();
    }

}
