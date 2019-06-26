
package com.aimlesshammer.pocpapi.client;

import java.util.Collections;
import java.util.List;
import com.aimlesshammer.pocpapi.configuration.WebClientConfiguration;
import com.aimlesshammer.pocpapi.model.CreditAccount;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import static org.assertj.core.api.Assertions.assertThat;

public class CreditAccountSAPIWireTest {

    private Integer portNumber = 8080;
    @Rule public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.wireMockConfig().port(portNumber));
    private final String urlRoot = "http://localhost:" + portNumber.toString();

    private String json = "[\n" +
        "  {\n" +
        "    \"customerId\": \"1\",\n" +
        "    \"creditCardNumber\": \"234\",\n" +
        "    \"balance\": \"567\"\n" +
        "  }\n" +
        "]";

    private ResponseDefinitionBuilder responseDefinitionBuilder = WireMock
        .aResponse()
        .withBody(json)
        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);

    /*@After
    public void tearDown(){
        WireMock.reset();
    }*/

    @Test
    public void itIs() {
        String customerId = "1";
        String urlTemplate = "/customer/{CUSTOMER_ID}/balance";
        String urlString = urlTemplate.replace("{CUSTOMER_ID}", customerId);
        CreditAccount creditAccount = new CreditAccount(customerId, "234", "567");
        List<CreditAccount> expected = Collections.singletonList(creditAccount);
        WireMock.stubFor(WireMock.get(urlString).willReturn(responseDefinitionBuilder));
        CreditAccountSAPI unit = new CreditAccountSAPI(new WebClientConfiguration().webClient(), urlRoot + urlTemplate);
        List<CreditAccount> actual = unit.creditCards(customerId);
        assertThat(actual).containsAll(expected);
        assertThat(expected).containsAll(actual);
    }

}
