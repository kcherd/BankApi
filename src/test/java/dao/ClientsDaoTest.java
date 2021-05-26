package dao;

import model.Card;
import model.Client;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientsDaoTest {
    private Connection connection;
    private ClientsDao clientsDao;

    @Before
    public void createConnection(){
        try {
            connection = DBConnect.getConnectionPool().getConnection();
            clientsDao = new ClientsDao(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getCards() throws Exception {
        List<Card> actual = new ArrayList<>();
        actual.add(new Card(1234567890123456L, "12345678901234567890", 1000));
        actual.add(new Card(1234567890123457L, "09876543210987654321", 120000));

        List<Card> expected = clientsDao.getCards("1234567890");

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = Exception.class)
    public void getCardsException() throws Exception{
        clientsDao.getCards("1234567899");
    }

    @Test
    public void checkBalance() throws Exception {
        double actual = 1000;
        double expected = clientsDao.checkBalance("1234567890", "12345678901234567890");

        Assert.assertEquals(expected, actual, 0.01);
    }

    @Test(expected = Exception.class)
    public void checkBalance_Exception_NoUser() throws Exception {
        clientsDao.checkBalance("1234567899", "12345678901234567890");
    }

    @Test(expected = Exception.class)
    public void checkBalance_Exception_NoAccess() throws Exception {
        clientsDao.checkBalance("1234567890", "12345678901234567891");
    }

    @Test
    public void depositOfFunds() throws Exception {
        boolean result = clientsDao.depositOfFunds("1234567890", 12345, "12345678901234567890");
        Assert.assertTrue(result);
    }

    @Test(expected = Exception.class)
    public void depositOfFunds_Exception_NoUser() throws Exception {
        clientsDao.depositOfFunds("1234567891", 12345, "12345678901234567890");
    }

    @Test(expected = Exception.class)
    public void depositOfFunds_Exception_NoAccess() throws Exception {
        clientsDao.depositOfFunds("1234567890", 12345, "12345678901234567899");
    }

    @Test
    public void newCard_TRUE() throws Exception {
        boolean result = clientsDao.newCard("1234567890", "12345678901234567890");
        Assert.assertTrue(result);
    }

    @Test(expected = Exception.class)
    public void newCard_Exception_NoUser() throws Exception {
        clientsDao.newCard("1234567891", "12345678901234567890");
    }

    @Test(expected = Exception.class)
    public void newCard_Exception_NoAccess() throws Exception {
        clientsDao.newCard("1234567890", "12345678901234567899");
    }
}