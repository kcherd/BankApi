package controller;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import model.Account;
import model.Amount;
import model.InData;
import model.Passport;

import java.io.*;

public class ClientHandler extends Handlers{
    private final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().toString();
        ApiMethods apiMethods = new ApiMethods();

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
                    out(exchange, String.valueOf(apiMethods.newCard((Account) parseInputStream(exchange, new Account()))));
                } catch (Exception e) {
                    errorAnswer(exchange, e.getMessage());
                }
                break;
            case "/client/cards/result":
                try {
                    String res = apiMethods.allCards((Passport) parseInputStream(exchange, new Passport()));
                    out(exchange, res);
                } catch (Exception e) {
                    errorAnswer(exchange, e.getMessage());
                }
                break;
            case "/client/balance/result":
                try {
                    out(exchange, String.valueOf(apiMethods.balance((Account) parseInputStream(exchange, new Account()))));
                } catch (Exception e) {
                    errorAnswer(exchange, e.getMessage());
                }
                break;
            case "/client/deposit/result":
                try{
                    out(exchange, String.valueOf(apiMethods.deposit((Amount) parseInputStream(exchange, new Amount()))));
                }catch (Exception e) {
                    errorAnswer(exchange, e.getMessage());
                }
                break;
            default:
                errorAnswer(exchange, "The page does not exist");
        }
    }

    private InData parseInputStream(HttpExchange exchange, InData format) throws IOException {
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

    private void showHTML(HttpExchange exchange, String name){
        StringBuilder stringBuilder = new StringBuilder();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(name))){
            String file;
            while ((file = bufferedReader.readLine()) != null){
                stringBuilder.append(file);
            }
            exchange.getResponseHeaders().set("Content-type", "text/html");
            exchange.sendResponseHeaders(200, 0);
            OutputStream outputStream = exchange.getResponseBody();

            outputStream.write(stringBuilder.toString().getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e){
            errorAnswer(exchange, e.getMessage());
        }
    }
}