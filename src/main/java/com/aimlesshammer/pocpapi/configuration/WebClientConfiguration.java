
package com.aimlesshammer.pocpapi.configuration;

import java.util.function.Function;

import com.aimlesshammer.pocpapi.client.SapiClient;
import com.aimlesshammer.pocpapi.model.CreditAccount;
import com.aimlesshammer.pocpapi.model.CurrentAccount;
import com.aimlesshammer.pocpapi.service.AccountService;
import io.netty.channel.ChannelOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Configuration
public class WebClientConfiguration {

    private final Logger logger = LoggerFactory.getLogger(WebClientConfiguration.class);
    private final Function<TcpClient, TcpClient> tcpClient = client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000);
    private final HttpClient httpClient = HttpClient.create().tcpConfiguration(tcpClient);
    private final ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
    private final ExchangeFilterFunction logging = (clientRequest, exchangeFunction) -> {
        logger.info("web client requesting: {} {}", clientRequest.method(), clientRequest.url());
        return exchangeFunction.exchange(clientRequest);
    };

    @Bean
    public WebClient webClient() {
        return WebClient
            .builder()
            .clientConnector(connector)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .defaultHeader(HttpHeaders.USER_AGENT, "Spring 5 WebClient")
            .filter(logging)
            .build();
    }

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
