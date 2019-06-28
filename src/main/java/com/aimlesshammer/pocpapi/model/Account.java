
package com.aimlesshammer.pocpapi.model;

import java.util.Objects;

public class Account {

    private String type;
    private String customerId;
    private String accountNumber;
    private String balance;

    public Account() {}

    public Account (String type, String customerId, String accountNumber, String balance) {
        this.type = type;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void setType(String type) {this.type = type;}
    public void setCustomerId(String customerId) {this.customerId = customerId;}
    public void setAccountNumber(String accountNumber) {this.accountNumber = accountNumber;}
    public void setBalance(String balance) {this.balance = balance;}

    public String getType() {return type;}
    public String getCustomerId() {return customerId;}
    public String getAccountNumber() {return accountNumber;}
    public String getBalance() {return balance;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(type, account.type) &&
            Objects.equals(customerId, account.customerId) &&
            Objects.equals(accountNumber, account.accountNumber) &&
            Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, customerId, accountNumber, balance);
    }

    @Override
    public String toString() {
        String string = "Account{type:%s;customerId:%s;accountNumber:%s;balance:%s}";
        return String.format(string, type, customerId, accountNumber, balance);
    }

}
