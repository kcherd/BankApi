package dao;

import controller.DBConnect;
import model.Card;
import model.Client;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ClientsDaoTest {
    private Connection connection;
    private ClientsDao clientsDao;
    private Client client;

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

        //сделать заглушки для клиента
        List<Card> actual = new ArrayList<>();
        actual.add(new Card(1234567890123456L, "12345678901234567890", 1000));
        actual.add(new Card(1234567890123457L, "09876543210987654321", 120000));

        List<Card> expected = clientsDao.getCards("1234567890");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkBalance() throws Exception {
        double actual = 1000;
        double expected = clientsDao.checkBalance("1234567890", "12345678901234567890");

        Assert.assertEquals(expected, actual, 0.01);
    }

    @Test
    public void depositOfFunds() {

    }

    @Test
    public void newCard() {
    }

    @Test
    public void getIdAccByNum() {

    }
}