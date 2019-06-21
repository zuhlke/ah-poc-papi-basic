
package com.aimlesshammer.pocpapi.model;

public class Account {

    private String type;
    private String customerId;
    private String accountNumber;
    private String balance;

    public Account (String type, String customerId, String accountNumber, String balance) {
        this.type = type;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getBalance() {
        return balance;
    }

}
