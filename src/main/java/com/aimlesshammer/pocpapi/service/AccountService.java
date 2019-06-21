
package com.aimlesshammer.pocpapi.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.aimlesshammer.pocpapi.Application;
import com.aimlesshammer.pocpapi.client.CreditAccountSAPI;
import com.aimlesshammer.pocpapi.client.CurrentAccountSAPI;
import com.aimlesshammer.pocpapi.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private CreditAccountSAPI creditAccountSAPI;
    private CurrentAccountSAPI currentAccountSAPI;

    public AccountService(CreditAccountSAPI creditAccountSAPI, CurrentAccountSAPI currentAccountSAPI) {
        this.creditAccountSAPI = creditAccountSAPI;
        this.currentAccountSAPI = currentAccountSAPI;
    }

    public List<Account> accounts(String customerId) {
        logger.info(Application.LOG_ID + ": requesting credit accounts for customer: '{}'", customerId);
        List<Account> creditAccounts = creditAccountSAPI
            .creditCards(customerId)
            .stream()
            .map(account -> new Account("credit", account.getCustomerId(), account.getCreditCardNumber(), account.getBalance()))
            .collect(Collectors.toList());
        logger.info(Application.LOG_ID + ": requesting current accounts for customer: '{}'", customerId);
        List<Account> currentAccounts = currentAccountSAPI
            .currentAccounts(customerId)
            .stream()
            .map(account -> new Account("current", account.getCustomerId(), account.getAccountNumber(), account.getBalance()))
            .collect(Collectors.toList());
        logger.info(Application.LOG_ID + ": returning credit and current accounts for customer: '{}'", customerId);
        return Stream
            .concat(creditAccounts.stream(), currentAccounts.stream())
            .collect(Collectors.toList());
    }

}
