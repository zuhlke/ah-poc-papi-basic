
package com.aimlesshammer.pocpapi.client;

import java.util.Collections;
import java.util.List;
import com.aimlesshammer.pocpapi.model.CurrentAccount;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

public class CurrentAccountSAPIMockTest {

    private RestTemplateBuilder mockRestTemplateBuilder = Mockito.mock(RestTemplateBuilder.class);
    private RestTemplate mockRestTemplate = Mockito.mock(RestTemplate.class);

    @Before
    public void setUp() {
        Mockito
            .when(mockRestTemplateBuilder.build())
            .thenReturn(mockRestTemplate);
    }

    @Test
    public void itReceivesOK() {
        String customerId = "1";
        String urlTemplate = "whatever/{CUSTOMER_ID}";
        String urlString = urlTemplate.replace("{CUSTOMER_ID}", customerId);
        CurrentAccount currentAccount = new CurrentAccount(customerId, "234", "567");
        List<CurrentAccount> expected = Collections.singletonList(currentAccount);
        ParameterizedTypeReference<List<CurrentAccount>> reference = new ParameterizedTypeReference<List<CurrentAccount>>() {};
        Mockito
            .when(
                mockRestTemplate.exchange(
                    urlString,
                    HttpMethod.GET,
                    null,
                    reference
                )
            )
            .thenReturn(new ResponseEntity<>(expected, HttpStatus.I_AM_A_TEAPOT));  // the HTTP status is irrelevant here
        CurrentAccountSAPI unit = new CurrentAccountSAPI(mockRestTemplateBuilder, urlTemplate);
        List<CurrentAccount> actual = unit.currentAccounts(customerId);
        assertThat(actual).containsAll(expected);
        assertThat(expected).containsAll(actual);
    }

}
