package dao;

import controller.BankServer;
import model.Card;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public class ClientsDaoTest {
    private ClientsDao clientsDao;

    @BeforeClass
    public static void Init() {
        BankServer.createPool();
        BankServer.startServer();
    }

    @Before
    public void createDAO() {
        clientsDao = new ClientsDao();
    }

    @Test
    public void getCards() throws Exception {
        List<Card> actual = new ArrayList<>();
        actual.add(new Card(3334567890123451L, "62345678101239566666", 2222));
        actual.add(new Card(5534567890123451L, "62345678101239555555", 3333));

        List<Card> expected = clientsDao.getCards("3637483693");

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = Exception.class)
    public void getCardsException() throws Exception{
        clientsDao.getCards("1234567899");
    }

    @Test
    public void checkBalance() throws Exception {
        double actual = 2222;
        double expected = clientsDao.checkBalance("3637483693", "62345678101239566666");

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
        boolean result = clientsDao.depositOfFunds("1234567890", 12345, "09876543210987654321");
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

//    @After
//    public void closeConn() throws SQLException {
//        connection.close();
//    }

    @AfterClass
    public static void stopServer(){
        BankServer.stopServer();
    }
}