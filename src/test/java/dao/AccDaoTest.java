package dao;

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
    public void checkBalance() throws Exception {
        double actual = 1000;
        double expected = accDao.checkBalance("12345678901234567890");

        Assert.assertEquals(expected, actual, 0.01);
    }

    @Test
    public void depositOfFunds_TRUE() throws Exception {
        boolean expected = accDao.depositOfFunds(555, "12345678901234567890");

        Assert.assertTrue(expected);
    }

    @Test(expected = Exception.class)
    public void depositOfFunds_Exception() throws Exception{
        accDao.depositOfFunds(-1, "12345678901234567890");
    }

    @Test
    public void newCard_TRUE() throws Exception {
        boolean expected = accDao.newCard("12345678901234567890");

        Assert.assertTrue(expected);
    }

    @Test(expected = Exception.class)
    public void newCard_Exception() throws Exception{
        accDao.newCard("1234567890123456789");
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