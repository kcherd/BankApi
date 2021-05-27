package controller;

import dao.DBConnect;
import model.Account;
import model.Amount;
import model.Passport;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class GetHandlerTest {
    private GetHandler getHandler;
    private Connection connection;

    @BeforeClass
    public static void Init() {
        BankServer.startServer();
    }

    @Before
    public void initMethods() throws SQLException {
        connection = DBConnect.getConnectionPool().getConnection();
        getHandler = new GetHandler(connection);
    }

    @Test public void
    handler_Cards(){
        String actual = "[{\"num\":1234567890123458,\"accNum\":\"12345678901214567890\",\"balance\":1543.0},{\"num\":1234567890123459,\"accNum\":\"12345678901234567891\",\"balance\":85743.0}]";
        given().get("http://localhost:8080/cards?passport=0987654321")
                .then()
                .body(equalTo(actual));
    }

    @Test public void
    handler_Cards_Error(){
       // BankServer.startServer();
        given().get("http://localhost:8080/cards?passport=1234567891")
                .then().
                body("error", equalTo("No user with such data"));
    }

    @Test public void
    handle_New() {
        //BankServer.startServer();
        given().get("http://localhost:8080/new?passport=1234567890&account=12345678901234567890")
                .then().
                body("result", equalTo(true));
    }

    @Test public void
    handle_New_Error() {
        given().get("http://localhost:8080/new?passport=1234567891&account=12345678901234567890")
                .then()
                .body("error", equalTo("No user with such data"));
    }

    @Test public void
    handle_balance() {
        given().get("http://localhost:8080/balance?passport=1234567890&account=12345678901234567890")
                .then()
                .body("result", is(1000F));
    }

    @Test public void
    handle_balance_Error() {
        given().get("http://localhost:8080/balance?passport=1234567890&account=12345678901234567891")
                .then()
                .body("error", equalTo("No access to account"));
    }

    @Test public void
    handler_deposit(){
        given().get("http://localhost:8080/deposit?passport=1234567890&amount=6473&account=12345678901234567890")
                .then()
                .body("result", is(true));
    }

    @Test public void
    handler_deposit_Error(){
        given().get("http://localhost:8080/deposit?passport=1234567890&amount=-1&account=12345678901234567890")
                .then()
                .body("error", equalTo("Amount < 0"));
    }

    @Test public void
    handler_errorPage(){
        given().get("http://localhost:8080/test?rtr=12")
                .then().
                body("error", equalTo("The page does not exist"));
    }

    @Test
    public void queryToMap() {
        Map<String, String> actual = new HashMap<>();
        actual.put("passport", "1234567890");
        actual.put("account", "12345678901234567890");

        Map<String, String> expected = getHandler.queryToMap("passport=1234567890&account=12345678901234567890");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parsQueryToPassport() {
        Passport actual = new Passport("1234567890");
        Passport expected = getHandler.parsQueryToPassport("passport=1234567890");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parsQueryToAccount() {
        Account actual = new Account("1234567890", "12345678901234567890");
        Account expected = getHandler.parsQueryToAccount("passport=1234567890&account=12345678901234567890");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parsQueryToAmount() {
        Amount actual = new Amount("1234567890", 1234, "12345678901234567890");
        Amount expected = getHandler.parsQueryToAmount("passport=1234567890&amount=1234&account=12345678901234567890");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void action() {
        String actual = "new";
        String expected = getHandler.action("/new?passport=1234567890&account=12345678901234567890");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void action_NULL(){
        String expected = getHandler.action("elboen");

        Assert.assertNull(expected);
    }

    @After
    public void stopConn() throws SQLException {
        connection.close();
    }

    @AfterClass
    public static void stopServer(){
        BankServer.stopServer();
    }
}