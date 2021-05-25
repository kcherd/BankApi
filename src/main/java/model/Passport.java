package model;

public class Passport implements InData{
    String passport;

    public Passport(){}

    public Passport(String passport) {
        this.passport = passport;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }
}