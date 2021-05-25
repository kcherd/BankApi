package controller;

import com.sun.net.httpserver.HttpExchange;
import model.Account;
import model.Amount;
import model.Passport;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperationHandler extends Handlers{

    @Override
    public void handle(HttpExchange exchange) {
        String url = exchange.getRequestURI().toString();
        String query = exchange.getRequestURI().getQuery();
        ApiMethods apiMethods = new ApiMethods();

        switch (action(url)){
            case "new":
                try {
                    out(exchange, String.valueOf(apiMethods.newCard(parsQueryToAccount(query))));
                } catch (Exception e) {
                    errorAnswer(exchange, e.getMessage());
                }
                break;
            case "cards":
                try {
                    out(exchange, apiMethods.allCards(parsQueryToPassport(query)));
                } catch (Exception e) {
                    errorAnswer(exchange, e.getMessage());
                }
                break;
            case "balance":
                try {
                    out(exchange, String.valueOf(apiMethods.balance(parsQueryToAccount(query))));
                } catch (Exception e) {
                    errorAnswer(exchange, e.getMessage());
                }
                break;
            case "deposit":
                try {
                    out(exchange, String.valueOf(apiMethods.deposit(parsQueryToAmount(query))));
                } catch (Exception e) {
                    errorAnswer(exchange, e.getMessage());
                }
                break;
            default:
                errorAnswer(exchange,"The page does not exist");
        }
    }

    /**
     * Функция парсинга параметров get запроса из строки в объект Map
     * @param query строка запроса
     * @return возвращает ассоциативный массив названий и значений параметров get запроса
     */
    public Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
    }

    public Passport parsQueryToPassport(String query){
        Map<String, String> map = queryToMap(query);
        return new Passport(map.get("passport"));
    }

    public Account parsQueryToAccount(String query){
        Map<String, String> map = queryToMap(query);
        return new Account(map.get("passport"), map.get("account"));
    }

    public Amount parsQueryToAmount(String query){
        Map<String, String> map = queryToMap(query);
        return new Amount(map.get("passport"), Double.parseDouble(map.get("amount")), map.get("account"));
    }

    /**
     * функция, извлекающая из url название действия, которое хочет выполнить пользователь
     * @param url запрос пользователя
     * @return возвращает название действия
     */
    public String action(String url){
        Pattern pattern = Pattern.compile("\\/(.*)\\?");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find())
        {
            return matcher.group(1);
        }
        return null;
    }
}