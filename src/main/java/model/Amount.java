package model;

import java.util.Objects;

/**
 * класс для параметро запроса
 */
public class Amount implements InData{
    String passport;
    double amount;
    String account;

    public Amount(){}

    public Amount(String passport, double amount, String account) {
        this.passport = passport;
        this.amount = amount;
        this.account = account;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount1 = (Amount) o;
        return Double.compare(amount1.amount, amount) == 0 && Objects.equals(passport, amount1.passport) && Objects.equals(account, amount1.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passport, amount, account);
    }
}
