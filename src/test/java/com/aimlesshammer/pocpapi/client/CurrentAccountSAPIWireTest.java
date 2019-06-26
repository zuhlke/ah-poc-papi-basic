
package com.aimlesshammer.pocpapi.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.aimlesshammer.pocpapi.model.CurrentAccount;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

public class CurrentAccountSAPIWireTest {

    @Rule public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.wireMockConfig().port(8080));
    private final CurrentAccountSAPI unit = new CurrentAccountSAPI(new RestTemplateBuilder(), "http://localhost:8080/whatever/{CUSTOMER_ID}");

    @Test
    public void itReceivesNonemptyList() {
        CurrentAccount currentAccount = new CurrentAccount("1", "234", "567");
        List<CurrentAccount> expected = Collections.singletonList(currentAccount);
        ResponseDefinitionBuilder responseDefinitionBuilder = aResponse()
            .withBody(json1())
            .withHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE);
        stubFor(get("/whatever/1").willReturn(responseDefinitionBuilder));
        List<CurrentAccount> actual = unit.currentAccounts("1");
        assertThat(actual).containsAll(expected);
        assertThat(expected).containsAll(actual);
    }

    @Test
    public void itReceivesEmptyList() {
        List<CurrentAccount> expected = new ArrayList<>();
        ResponseDefinitionBuilder responseDefinitionBuilder = aResponse()
            .withBody(json2())
            .withHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE);
        stubFor(get("/whatever/1").willReturn(responseDefinitionBuilder));
        List<CurrentAccount> actual = unit.currentAccounts("1");
        assertThat(actual).containsAll(expected);
        assertThat(expected).containsAll(actual);
    }

    @Test(expected = Exception.class)
    public void itReceivesException() {
        ResponseDefinitionBuilder responseDefinitionBuilder = aResponse()
            .withStatus(SC_BAD_REQUEST)
            .withHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE);
        stubFor(get("/whatever/1").willReturn(responseDefinitionBuilder));
        List<CurrentAccount> boom = unit.currentAccounts("1");
    }

    @Test
    public void itTriesOnce() {
        ResponseDefinitionBuilder responseDefinitionBuilder = aResponse()
            .withStatus(SC_BAD_REQUEST)
            .withHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE);
        stubFor(get("/whatever/1").willReturn(responseDefinitionBuilder));
        try {
            List<CurrentAccount> boom = unit.currentAccounts("1");
            fail("que?");
        }
        catch (Exception e) {
            verify(1, getRequestedFor(urlEqualTo("/whatever/1")));
        }
    }

    private static String json1() {
        return "[{\"customerId\": \"1\", \"accountNumber\": \"234\", \"balance\": \"567\"}]";
    }

    private static String json2() {
        return "[]";
    }

}
