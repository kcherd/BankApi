package controller;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import dao.DBConnect;
import model.*;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class HandlersTest {
    Handlers handlers;
    Gson gson = new Gson();

    @Before
    public void Init() throws SQLException {
        JdbcConnectionPool connectionPool = DBConnect.getConnectionPool();
        handlers = new GetHandler(connectionPool.getConnection());
    }

    @Test
    public void handle() {
    }

    @Test
    public void newCard() throws Exception {
        Gson gson = new Gson();
        Account account = new Account("1234567890", "12345678901234567890");
        String expected = handlers.newCard(account);
        Result result = new Result(true);
        String actual = gson.toJson(result);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void allCards() throws Exception {
        Gson gson = new Gson();
        Passport passport = new Passport("1234567890");
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(1234567890123456L, "12345678901234567890", 1000));
        cards.add(new Card(1234567890123457L, "09876543210987654321", 120000));
        String actual = gson.toJson(cards);

        String expected = handlers.allCards(passport);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void balance() throws Exception {
        Account account = new Account("1234567890", "12345678901234567890");
        String expected = handlers.balance(account);
        String actual = gson.toJson(new Result(1000));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deposit() throws Exception {
        Amount amount = new Amount("1234567890", 231, "12345678901234567890");
        String expected = handlers.deposit(amount);
        String actual = gson.toJson(new Result(true));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void errorAnswer() {
    }

    @Test
    public void out() {
    }

    @Test
    public void testOut() {

    }
}