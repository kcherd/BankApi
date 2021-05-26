package model;

import java.util.Objects;

/**
 * класс для параметро запроса
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passport passport1 = (Passport) o;
        return Objects.equals(passport, passport1.passport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passport);
    }
}