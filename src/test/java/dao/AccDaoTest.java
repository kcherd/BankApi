package dao;

import controller.BankServer;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;

public class AccDaoTest {
    private Connection connection;
    private AccDao accDao;

    @BeforeClass
    public static void Init() {
        BankServer.createPool();
        BankServer.startServer();
    }

    @Before
    public void getConnection() throws SQLException {
        connection = BankServer.getConnection();
        accDao = new AccDao();
        accDao.setConnection(connection);
    }

    @Test
    public void checkBalance() throws Exception {
        double actual = 40000;
        double expected = accDao.checkBalance("82345678101239567890");

        Assert.assertEquals(expected, actual, 0.01);
    }

    @Test
    public void depositOfFunds_TRUE() throws Exception {

        boolean expected = accDao.depositOfFunds(555, "32345678101239567890");
        connection = BankServer.getConnection();
        accDao.setConnection(connection);
        double result = accDao.checkBalance("32345678101239567890");
        Assert.assertEquals(result, 655, 0.01);
        Assert.assertTrue(expected);
    }

    @Test(expected = Exception.class)
    public void depositOfFunds_Exception() throws Exception{
        accDao.depositOfFunds(-1, "12345678901234567890");
    }

    @Test
    public void newCard_TRUE() throws Exception {
        boolean expected = accDao.newCard("32345678101239567888");
        ClientsDao clientsDao = new ClientsDao();
        connection = BankServer.getConnection();
        clientsDao.setConnection(connection);
        int count = clientsDao.getCards("2637483692").size();
        Assert.assertEquals(count, 1);

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

//    @After
//    public void closeConn() throws SQLException {
//        connection.close();
//    }

    @AfterClass
    public static void stopServer(){
        BankServer.stopServer();
    }
}