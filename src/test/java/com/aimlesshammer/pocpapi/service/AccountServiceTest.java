
package com.aimlesshammer.pocpapi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.aimlesshammer.pocpapi.client.CreditAccountSAPI;
import com.aimlesshammer.pocpapi.client.CurrentAccountSAPI;
import com.aimlesshammer.pocpapi.model.Account;
import com.aimlesshammer.pocpapi.model.CreditAccount;
import com.aimlesshammer.pocpapi.model.CurrentAccount;
import org.junit.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountServiceTest {

    private final CreditAccountSAPI creditAccountSAPI = Mockito.mock(CreditAccountSAPI.class);
    private final CurrentAccountSAPI currentAccountSAPI = Mockito.mock(CurrentAccountSAPI.class);
    private final AccountService unit = new AccountService(creditAccountSAPI, currentAccountSAPI);

    @Test
    public void itReturnsNonemptyList_WhenCustomerHasAccounts() {
        Mockito
            .when(creditAccountSAPI.creditAccounts("1"))
            .thenReturn(Collections.singletonList(new CreditAccount("1", "234", "567")));
        Mockito
            .when(currentAccountSAPI.currentAccounts("1"))
            .thenReturn(Collections.singletonList(new CurrentAccount("1", "234", "567")));
        List<Account> expected = Arrays.asList(new Account("credit", "1", "234", "567"), new Account("current", "1", "234", "567"));
        List<Account> actual = unit.accounts("1");
        assertThat(actual).containsAll(expected);
        assertThat(expected).containsAll(actual);
    }

    @Test
    public void itReturnsEmptyList_WhenCustomerHasNoAccounts() {
        Mockito
            .when(creditAccountSAPI.creditAccounts("2"))
            .thenReturn(new ArrayList<>());
        Mockito
            .when(currentAccountSAPI.currentAccounts("w"))
            .thenReturn(new ArrayList<>());
        List<Account> expected = new ArrayList<>();
        List<Account> actual = unit.accounts("2");
        assertThat(actual).containsAll(expected);
        assertThat(expected).containsAll(actual);
    }

}
