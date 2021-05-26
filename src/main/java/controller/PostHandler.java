package controller;

import com.sun.net.httpserver.HttpExchange;
import model.Account;
import model.Amount;
import model.InData;
import model.Passport;

import java.io.*;
import java.sql.Connection;

/**
 * Класс для обработки post запросов
 */
public class PostHandler extends Handlers{

    /**
     * Конструктор - наследует радительский конструктор
     */
    public PostHandler(){
        super();
    }

    public PostHandler(Connection connection) {super(connection);}

    /**
     * процедура, обрабатывающия http запросы
     * @param exchange
     */
    @Override
    public void handle(HttpExchange exchange){
        String url = exchange.getRequestURI().toString();

        switch (url){
            case "/client":
                showHTML(exchange, "./src/main/resources/options.html");
                break;
            case "/client/new":
                showHTML(exchange, "./src/main/resources/new.html");
                break;
            case "/client/cards":
                showHTML(exchange, "./src/main/resources/cards.html");
                break;
            case "/client/balance":
                showHTML(exchange, "./src/main/resources/balance.html");
                break;
            case "/client/deposit":
                showHTML(exchange, "./src/main/resources/deposit.html");
                break;
            case "/client/new/result":
                try {
                    String result = newCard((Account) parseInputStream(exchange, new Account()));
                    out(exchange, result);
                } catch (Exception e) {
                    errorAnswer(exchange, e.getMessage());
                }
                break;
            case "/client/cards/result":
                try {
                    String res = allCards((Passport) parseInputStream(exchange, new Passport()));
                    out(exchange, res);
                } catch (Exception e) {
                    errorAnswer(exchange, e.getMessage());
                }
                break;
            case "/client/balance/result":
                try {
                    String result = balance((Account) parseInputStream(exchange, new Account()));
                    out(exchange, result);
                } catch (Exception e) {
                    errorAnswer(exchange, e.getMessage());
                }
                break;
            case "/client/deposit/result":
                try{
                    String result = deposit((Amount) parseInputStream(exchange, new Amount()));
                    out(exchange, result);
                }catch (Exception e) {
                    errorAnswer(exchange, e.getMessage());
                }
                break;
            default:
                errorAnswer(exchange, "The page does not exist");
        }
    }

    /**
     * Функция парсинга тела POST запросов в объекты классов Account, Amount, Passport
     * @param exchange
     * @param format классб в который нужно преобразовать тело запроса
     * @return объект класса Account/Amount/Passport
     * @throws IOException
     */
    public InData parseInputStream(HttpExchange exchange, InData format) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        System.out.println(exchange.getRequestBody());
        int ch;
        StringBuilder sb = new StringBuilder();
        while ((ch = inputStream.read()) != -1){
            sb.append((char) ch);
        }
        System.out.println(sb.toString());

        format = gson.fromJson(sb.toString(), format.getClass());
        return format;
    }

    /**
     * процедура вывода html страниц
     * @param exchange
     * @param name название страницы
     */
    public void showHTML(HttpExchange exchange, String name){
        StringBuilder stringBuilder = new StringBuilder();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(name))){
            String file;
            while ((file = bufferedReader.readLine()) != null){
                stringBuilder.append(file);
            }

            out(exchange, stringBuilder.toString(), "text/html");
        } catch (IOException e){
            errorAnswer(exchange, e.getMessage());
        }
    }
}