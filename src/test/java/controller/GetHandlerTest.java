package controller;

import dao.DBConnect;
import model.Account;
import model.Amount;
import model.Passport;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.when;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.DOUBLE;
import static org.hamcrest.Matchers.*;

public class GetHandlerTest {
    GetHandler getHandler;

    @Before
    public void Init() throws SQLException {
        JdbcConnectionPool connectionPool = DBConnect.getConnectionPool();
        Connection connection = connectionPool.getConnection();
        getHandler = new GetHandler(connection);
    }


    @Test public void
    handle_New() {
        BankServer.startServer();
        when().get("http://localhost:8080/new?passport=1234567890&account=12345678901234567890")
                .then().statusCode(200)
                .assertThat().body("result", equalTo(true));
    }

    @Test public void
    handle_balance() {
        BankServer.startServer();
        when().get("http://localhost:8080/balance?passport=1234567890&account=12345678901234567890")
                .then().statusCode(200)
                .assertThat().body("result", is(1000F));
    }

    @Test public void
    handler_deposit(){
        BankServer.startServer();
        when().get("http://localhost:8080/deposit?passport=1234567890&amount=6473&account=12345678901234567890")
                .then().statusCode(200)
                .assertThat().body("result", is(true));
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
}