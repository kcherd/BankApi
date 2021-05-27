package model;

import java.util.List;

public class Counterparty {
    private long id;
    private long inn;
    private long ogrn;
    private List<CounterpartyAccount> counterpartyAccounts;

    public Counterparty(){}

    public Counterparty(long id, long inn, long ogrn, List<CounterpartyAccount> counterpartyAccounts) {
        this.id = id;
        this.inn = inn;
        this.ogrn = ogrn;
        this.counterpartyAccounts = counterpartyAccounts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getInn() {
        return inn;
    }

    public void setInn(long inn) {
        this.inn = inn;
    }

    public long getOgrn() {
        return ogrn;
    }

    public void setOgrn(long ogrn) {
        this.ogrn = ogrn;
    }

    public List<CounterpartyAccount> getCounterpartyAccounts() {
        return counterpartyAccounts;
    }

    public void setCounterpartyAccounts(List<CounterpartyAccount> counterpartyAccounts) {
        this.counterpartyAccounts = counterpartyAccounts;
    }

    public static class CounterpartyAccount{
        private String num;
        private double balance;

        public CounterpartyAccount(){}

        public CounterpartyAccount(String num, double balance) {
            this.num = num;
            this.balance = balance;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
    }
}
