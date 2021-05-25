package dao;

import controller.DBConnect;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class AccDaoTest {
    private Connection connection;
    private AccDao accDao;

    @Before
    public void getConnection() throws SQLException {
        connection = DBConnect.getConnectionPool().getConnection();
        accDao = new AccDao(connection);
    }

    @Test
    public void checkBalance() throws SQLException {
        double actual = 1000;
        double expected = accDao.checkBalance("12345678901234567890");

        Assert.assertEquals(expected, actual, 0.01);
    }

    @Test
    public void depositOfFunds_TRUE() throws SQLException {
        boolean expected = accDao.depositOfFunds(555, "12345678901234567890");

        Assert.assertTrue(expected);
    }

    @Test
    public void depositOfFunds_FALSE() throws SQLException {
        boolean expected = accDao.depositOfFunds(555, "12345678901234457890");

        Assert.assertFalse(expected);
    }

    @Test
    public void newCard_TRUE() throws SQLException {
        boolean expected = accDao.newCard("12345678901234567890");

        Assert.assertTrue(expected);
    }

    @Test
    public void newCard_FALSE() throws SQLException {
        boolean expected = accDao.newCard("12345678901234567811");

        Assert.assertFalse(expected);
    }

    @Test
    public void findAccByNum() throws SQLException {
        long actual = 4;
        long expected = accDao.findAccByNum("12345678901234567891");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findCardByNum() throws SQLException {
        long actual = 5;
        long expected = accDao.findCardByNum(1234567890123450L);

        Assert.assertEquals(expected, actual);
    }
}