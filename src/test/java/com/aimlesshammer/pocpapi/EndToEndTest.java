
package com.aimlesshammer.pocpapi;

import java.util.List;
import com.aimlesshammer.pocpapi.model.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EndToEndTest {

    @Before
    public void setUp() {
        PapiApplication.main(new String[0]);
    }

    @After
    public void tearDown() {
        PapiApplication.stop(0);
    }

    @Test
    public void itIs() {
        String expected = "[Account{type:credit;customerId:10101010;accountNumber:1234567890;balance:1234.50}, Account{type:current;customerId:10101010;accountNumber:64746383648;balance:34.50}]";
        String actual = new RestTemplateBuilder()
            .build()
            .exchange(
                "http://localhost:8080/balance?customer-id=10101010",
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<List<Account>>() {}
            )
            .getBody()
            .toString();
        assertThat(actual).isEqualTo(expected);
    }

}
