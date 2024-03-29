
package com.aimlesshammer.pocpapi.model;

import java.util.Objects;

public class CreditAccount {

    private String customerId;
    private String creditCardNumber;
    private String balance;

    public CreditAccount(String customerId, String creditCardNumber, String balance) {
        this.customerId = customerId;
        this.creditCardNumber = creditCardNumber;
        this.balance = balance;
    }

    public CreditAccount() {}

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        String string = "CreditAccount{customerId:%s;creditCardNumber:%s;balance:%s}";
        return String.format(string, customerId, creditCardNumber, balance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditAccount that = (CreditAccount) o;
        return Objects.equals(customerId, that.customerId) &&
                Objects.equals(creditCardNumber, that.creditCardNumber) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, creditCardNumber, balance);
    }

}
