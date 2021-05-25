package model;

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
}
