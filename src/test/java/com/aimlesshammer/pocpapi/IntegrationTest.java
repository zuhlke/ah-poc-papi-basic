
package com.aimlesshammer.pocpapi;

import java.util.Arrays;
import java.util.List;
import com.aimlesshammer.pocpapi.configuration.WebClientConfiguration;
import com.aimlesshammer.pocpapi.model.Account;
import com.aimlesshammer.pocpapi.service.AccountService;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class IntegrationTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);
    private final AccountService accountService = new WebClientConfiguration().accountService(
        "https://ah-poc-sapi-cc-bal.cfapps.io/customer/{CUSTOMER_ID}/balance",
        "https://ah-poc-sapi-ca-bal.cfapps.io/customer/{CUSTOMER_ID}/balance",
        2,
        new WebClientConfiguration().webClient()
    );
    private final Controller controller = new Controller(accountService);

    @Test
    public void itReceivesNonemptyList() {
        List<Account> expected = Arrays.asList(
            new Account("credit", "10101010", "1234567890", "1234.50"),
            new Account("current", "10101010", "64746383648", "34.50")
        );
        List<Account> actual = controller.getBalances("10101010");
        assertThat(expected).isEqualTo(actual);
    }

}
