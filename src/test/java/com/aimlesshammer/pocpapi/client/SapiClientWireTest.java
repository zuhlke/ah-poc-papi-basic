
package com.aimlesshammer.pocpapi.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.aimlesshammer.pocpapi.configuration.WebClientConfiguration;
import com.aimlesshammer.pocpapi.model.CreditAccount;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

public class SapiClientWireTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);
    private final Integer retryCount = 2;
    private final Class responseClass = CreditAccount[].class;
    private final SapiClient unit = new SapiClient(
        new WebClientConfiguration().webClient(),
        "http://localhost:8080/whatever/{CUSTOMER_ID}",
        retryCount,
        responseClass
    );

    @Test
    public void itReceivesNonemptyList_WhenCustomerHasCard() {
        CreditAccount creditAccount = new CreditAccount("1", "234", "567");
        List<CreditAccount> expected = Collections.singletonList(creditAccount);
        ResponseDefinitionBuilder responseDefinitionBuilder = aResponse()
            .withBody(json1())
            .withHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE);
        stubFor(get("/whatever/1").willReturn(responseDefinitionBuilder));
        List<CreditAccount> actual = unit.get("1");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void itReceivesEmptyList_WhenCustomerHasNoCards() {
        List<CreditAccount> expected = new ArrayList<>();
        ResponseDefinitionBuilder responseDefinitionBuilder = aResponse()
            .withBody(json2())
            .withHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE);
        stubFor(get("/whatever/1").willReturn(responseDefinitionBuilder));
        List<CreditAccount> actual = unit.get("1");
        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = WebClientResponseException.class)
    public void itReceivesException_WhenResponseIsBad() {
        ResponseDefinitionBuilder responseDefinitionBuilder = aResponse()
            .withStatus(SC_BAD_REQUEST)
            .withHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE);
        stubFor(get("/whatever/1").willReturn(responseDefinitionBuilder));
        List<CreditAccount> boom = unit.get("1");
    }

    @Test
    public void itTriesThrice_WhenResponseNotImmediatelyOK() {
        ResponseDefinitionBuilder responseDefinitionBuilder = aResponse()
            .withStatus(SC_BAD_REQUEST)
            .withHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE);
        stubFor(get("/whatever/1").willReturn(responseDefinitionBuilder));
        try {
            List<CreditAccount> boom = unit.get("1");
            fail("should not reach this point");
        } catch (Exception e) {
            verify(retryCount + 1, getRequestedFor(urlEqualTo("/whatever/1")));
        }
    }

    private static String json1() {
        return "[{\"customerId\": \"1\", \"creditCardNumber\": \"234\", \"balance\": \"567\"}]";
    }

    private static String json2() {
        return "[]";
    }

}
