package controller;

import com.google.gson.Gson;
import model.*;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class HandlersTest {
    private Handlers handlers;
    private final Gson gson = new Gson();
    //private Connection connection;

    @BeforeClass
    public static void start(){
        BankServer.createPool();
        BankServer.startServer();
    }

    @Before
    public void Init() {
        //connection = BankServer.getConnection();
        handlers = new GetHandler();
    }

    @Test
    public void newCard() throws Exception {
        Gson gson = new Gson();
        Account account = new Account("0987654321", "12345678901214567890");
        String expected = handlers.newCard(account);
        Result result = new Result(true);
        String actual = gson.toJson(result);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void allCards() throws Exception {
        Gson gson = new Gson();
        Passport passport = new Passport("5674832916");
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(1234567890123450L, "12345678901239567890", 200000));
        cards.add(new Card(1234567890123451L, "12345678941234567890", 49385));
        String actual = gson.toJson(cards);

        String expected = handlers.allCards(passport);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void balance() throws Exception {
        Account account = new Account("0987654321", "12345678901234567891");
        String expected = handlers.balance(account);
        String actual = gson.toJson(new Result(85743.0));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deposit() throws Exception {
        Amount amount = new Amount("0987654321", 231, "12345678901214567890");
        String expected = handlers.deposit(amount);
        String actual = gson.toJson(new Result(true));

        Assert.assertEquals(expected, actual);
    }

//    @After
//    public void closeConn() throws SQLException {
//        connection.close();
//    }

    @AfterClass
    public static void stop(){
        BankServer.stopServer();
    }
}