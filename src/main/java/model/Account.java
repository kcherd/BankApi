package model;

import java.util.Objects;

/**
 * класс для параметро запроса
 */
public class Account implements InData{
    String passport;
    String account;

    public Account(){}

    public Account(String passport, String account) {
        this.passport = passport;
        this.account = account;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
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
        Account account1 = (Account) o;
        return Objects.equals(passport, account1.passport) && Objects.equals(account, account1.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passport, account);
    }
}
