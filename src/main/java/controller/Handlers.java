package controller;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.ClientsDao;
import model.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Абстрактный класс для описания действий при обработке Http запросов
 */
public abstract class Handlers implements HttpHandler {
    protected Gson gson;
    protected ClientsDao clientsDao;

    /**
     * Конструктор - инициализация полей класса
     */
    public Handlers(){
        gson = new Gson();
        clientsDao = new ClientsDao();
    }

    /**
     * абстрактный метод обработки запросов
     * @param exchange
     * @throws IOException
     */
    @Override
    public abstract void handle(HttpExchange exchange) throws IOException;

    /**
     * Метод создания новой карты
     * @param account параметры запроса: серия и номер паспотра, номер счета
     * возвращает true, если карта создана и страницу с ошибкой, если карта не создана
     */
    public String newCard(Account account) throws Exception {
        Result result = new Result(clientsDao.newCard(account.getPassport(), account.getAccount()));
        return gson.toJson(result);
    }

    /**
     * Метод вывода всех карт
     * @param passport серия и номер паспорта
     * возвращает json объект со спискои карт пользователя или описание ошибки
     */
    public String allCards(Passport passport) throws Exception {
        List<Card> cards = clientsDao.getCards(passport.getPassport());
        return gson.toJson(cards);
    }

    /**
     * Метод получения баланса по номеру сечта
     * @param account параметры: номер и серия паспорта, номер счета
     * выводит баланс или информацию об ошибке
     */
    public String balance(Account account) throws Exception {
        Result result = new Result(clientsDao.checkBalance(account.getPassport(), account.getAccount()));
        return gson.toJson(result);
    }

    /**
     * Метод производящий пополнение баланса счета
     * @param amount: серия и номер паспорта, сумма, номер счета
     * выводит true или сообщение об ошибке
     */
    public String deposit(Amount amount) throws Exception {
        Result result = new Result(clientsDao.depositOfFunds(amount.getPassport(), amount.getAmount(), amount.getAccount()));
        return gson.toJson(result);
    }

    /**
     * процедура вывода ошибки, вызывается в случае возникновения Exception при выполнении программы
     * @param httpExchange
     * @param message информация об ошибке
     */
    public void errorAnswer(HttpExchange httpExchange, String message){
        String jsonStr = makeJson(message);
        //System.out.println(jsonStr);
        out(httpExchange, jsonStr);
    }

    /**
     * функция создания json объекта из строки с описанием ошибки
     * @param message описание ошибки
     * @return json объект
     */
    private static String makeJson(String message){
        StringBuilder sb = new StringBuilder();
        sb.append("{\"error\":\"");
        sb.append(message);
        sb.append("\"}");
        //System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * процедура вывода ответа в формате json
     * @param exchange
     * @param result ответ
     */
    public void out(HttpExchange exchange, String result){
        out(exchange, result, "application/json");
    }

    /**
     * процедура вывода ответа в заданном формате
     * @param exchange
     * @param result ответ
     * @param contextType тип ответа
     */
    public void out(HttpExchange exchange, String result, String contextType){
        try {
            exchange.getResponseHeaders().set("Content-type", contextType);
            exchange.sendResponseHeaders(200, 0);
            OutputStream output = exchange.getResponseBody();

            output.write(result.getBytes());
            output.flush();
            output.close();
        }catch (IOException e){
            errorAnswer(exchange, e.getMessage());
        }
    }
}