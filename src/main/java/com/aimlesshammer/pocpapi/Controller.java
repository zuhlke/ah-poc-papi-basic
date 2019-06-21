
package com.aimlesshammer.pocpapi;

import java.util.List;
import com.aimlesshammer.pocpapi.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.aimlesshammer.pocpapi.model.Account;

@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private AccountService accountService;

    public Controller(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/balance")
    public List<Account> getBalances(@RequestParam("customer-id") String customerId) {
        logger.info(Application.LOG_ID + ": requesting balances for customer: '{}'", customerId);
        List<Account> accounts = accountService.accounts(customerId);
        logger.info(Application.LOG_ID + ": returning aggregated balances for customer: '{}', balances: '{}'", customerId, accounts);
        return accounts;
    }

}
