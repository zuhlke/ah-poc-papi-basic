
package com.aimlesshammer.pocpapi.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.aimlesshammer.pocpapi.PapiApplication;
import com.aimlesshammer.pocpapi.client.SapiClient;
import com.aimlesshammer.pocpapi.model.Account;
import com.aimlesshammer.pocpapi.model.CreditAccount;
import com.aimlesshammer.pocpapi.model.CurrentAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private SapiClient<CreditAccount> sapiCreditAccount;
    private SapiClient<CurrentAccount> sapiCurrentAccount;

    public AccountService(SapiClient sapiCreditAccount, SapiClient sapiCurrentAccount) {
        this.sapiCreditAccount = sapiCreditAccount;
        this.sapiCurrentAccount = sapiCurrentAccount;
    }

    public List<Account> accounts(String customerId) {
        logger.info(PapiApplication.LOG_ID + ": requesting credit accounts for customer: '{}'", customerId);
        logger.info(PapiApplication.LOG_ID + ": requesting current accounts for customer: '{}'", customerId);

        // todo: make this useful ??
        logger.info(PapiApplication.LOG_ID + ": returning credit and current accounts for customer: '{}'", customerId);

        return Stream
                .concat(sapiCreditAccount
                            .get(customerId)
                            .stream()
                            .map(account -> new Account("credit", account.getCustomerId(), account.getCreditCardNumber(), account.getBalance())),
                        sapiCurrentAccount
                            .get(customerId)
                            .stream()
                            .map(account -> new Account("current", account.getCustomerId(), account.getAccountNumber(), account.getBalance())) )
                .collect(Collectors.toList());
    }

}
