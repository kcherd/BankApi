package model;

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
}
