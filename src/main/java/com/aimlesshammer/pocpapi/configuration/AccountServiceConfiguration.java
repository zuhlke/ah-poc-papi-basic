
package com.aimlesshammer.pocpapi.configuration;

import com.aimlesshammer.pocpapi.client.SapiClient;
import com.aimlesshammer.pocpapi.model.CreditAccount;
import com.aimlesshammer.pocpapi.model.CurrentAccount;
import com.aimlesshammer.pocpapi.service.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AccountServiceConfiguration {

    @Bean
    public AccountService accountService(
            @Value("${sapi.creditCard.url}") String creditCardUrl,
            @Value("${sapi.currentAccount.url}") String currentCardUrl,
            @Value("${sapi.retryCount}") Integer retryCount,
            WebClient webClient
    ) {
        return new AccountService(
                new SapiClient(webClient, creditCardUrl, retryCount, CreditAccount[].class ),
                new SapiClient(webClient, currentCardUrl, retryCount, CurrentAccount[].class )
        );
    }

}
