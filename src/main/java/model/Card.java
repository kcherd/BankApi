package model;

import java.util.Objects;

/**
 * Модель банковской карты
 */
public class Card {
    private long num;
    private String accNum;
    private double balance;

    public Card(){}

    public Card(long num, String accNum, double balance) {
        this.num = num;
        this.accNum = accNum;
        this.balance = balance;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Card{" +
                "num=" + num +
                ", accNum='" + accNum + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return num == card.num && Double.compare(card.balance, balance) == 0 && Objects.equals(accNum, card.accNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, accNum, balance);
    }
}