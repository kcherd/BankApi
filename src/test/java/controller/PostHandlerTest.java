package controller;

import org.junit.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostHandlerTest {

    @BeforeClass
    public static void Init(){
        BankServer.createPool();
        BankServer.startServer();
    }

    @Test public void
    handler_cards_result(){
        String actual = "[{\"num\":2234567890123451,\"accNum\":\"72345678101239567890\",\"balance\":3000.0}]";
        given().body("{\"passport\":\"9876546372\"}")
                .post("http://localhost:8080/client/cards/result")
                .then()
                .body(equalTo(actual));
    }

    @Test public void
    handler_cards_result_error(){
        given().body("{\"passport\":\"1234567891\"}")
                .post("http://localhost:8080/client/cards/result")
                .then()
                .body("error", equalTo("No user with such data"));
    }

    @Test public void
    handler_new_result() {
        given().body("{\"passport\":\"1234567890\",\"account\":\"12345678901234567890\"}").when()
               .post("http://localhost:8080/client/new/result")
               .then()
               .body("result", equalTo(true));
    }

    @Test public void
    handler_new_result_error() {
        given().body("{\"passport\":\"1234567891\",\"account\":\"12345678901234567890\"}").when()
                .post("http://localhost:8080/client/new/result")
                .then()
                .body("error", equalTo("No user with such data"));
    }

    @Test public void
    handler_balance_result() {
        given().body("{\"passport\":\"9876546372\",\"account\":\"72345678101239567890\"}").when()
                .post("http://localhost:8080/client/balance/result")
                .then()
                .body("result", equalTo(3000F));
    }

    @Test public void
    handler_balance_result_error() {
        given().body("{\"passport\":\"1234567890\",\"account\":\"12345678901234567891\"}").when()
                .post("http://localhost:8080/client/balance/result")
                .then()
                .body("error", equalTo("No access to account"));
    }

    @Test public void
    handler_deposit_result() {
        given().body("{\"passport\":\"1234567890\",\"amount\":12345,\"account\":\"12345678901234567890\"}").when()
                .post("http://localhost:8080/client/deposit/result")
                .then()
                .body("result", equalTo(true));
    }

    @Test public void
    handler_deposit_result_error() {
        given().body("{\"passport\":\"1234567890\",\"amount\":12345,\"account\":\"12345678901234567891\"}").when()
                .post("http://localhost:8080/client/deposit/result")
                .then()
                .body("error", equalTo("No access to account"));
    }

    @Test public void
    handler_error_page(){
        given().post("http://localhost:8080/client/utkmg")
                .then()
                .body("error", equalTo("The page does not exist"));
    }

    @Test public void
    handler_client(){
        String actual = "<!DOCTYPE html><html lang=\"en\"><head>    <meta charset=\"UTF-8\">    <title>options</title></head><body><a href=\"http://localhost:8080/client/new\">Выпуск новой карты по счету</a><br><a href=\"http://localhost:8080/client/cards\">Просмотр списка карт</a><br><a href=\"http://localhost:8080/client/balance\">Проверка баланса</a><br><a href=\"http://localhost:8080/client/deposit\">Внесение средств</a><br></body></html>";
        given().post("http://localhost:8080/client")
                .then()
                .body(equalTo(actual));
    }

    @Test public void
    handler_client_new(){
        String actual = "<!DOCTYPE html><html lang=\"en\"><head>    <meta charset=\"UTF-8\">    <title>New card</title></head><body><p>Create a new card</p><form name=\"f1\">    <p>Passport</p>    <input type=\"text\" name=\"passport\"><br>    <p>Account</p>    <input type=\"text\" name=\"acc\"><br>    <p><input type=\"submit\"></p></form></body></html>";
        given().post("http://localhost:8080/client/new")
                .then()
                .body(equalTo(actual));
    }

    @Test public void
    handler_client_cards(){
        String actual = "<!DOCTYPE html><html><head>    <title>All cards</title></head><body><p>All cards</p><form id = \"f1\" method=\"post\">    <p>Passport</p>    <input type=\"text\" name=\"passport\"><br>    <p><input type=\"submit\"></p></form></body></html>";
        given().post("http://localhost:8080/client/cards")
                .then()
                .body(equalTo(actual));
    }

    @Test public void
    handler_client_balance(){
        String actual = "<!DOCTYPE html><html lang=\"en\"><head>    <meta charset=\"UTF-8\">    <title>Check balance</title></head><body><p>Check your balance</p><form name=\"f1\">    <p>Passport</p>    <input type=\"text\" name=\"passport\"><br>    <p>Account</p>    <input type=\"text\" name=\"acc\"><br>    <p><input type=\"submit\"></p></form></body></html>";
        given().post("http://localhost:8080/client/balance")
                .then()
                .body(equalTo(actual));
    }

    @Test public void
    handler_client_deposit(){
        String actual = "<!DOCTYPE html><html lang=\"en\"><head>    <meta charset=\"UTF-8\">    <title>Deposit</title></head><body><p>Top up an account</p><form name=\"f1\">    <p>Passport</p>    <input type=\"text\" name=\"passport\"><br>    <p>Amount</p>    <input type=\"text\" name=\"amount\"><br>    <p>Account</p>    <input type=\"text\" name=\"acc\"><br>    <p><input type=\"submit\"></p></form></body></html>";
        given().post("http://localhost:8080/client/deposit")
                .then()
                .body(equalTo(actual));
    }

    @AfterClass
    public static void stopServer(){
        BankServer.stopServer();
    }
}