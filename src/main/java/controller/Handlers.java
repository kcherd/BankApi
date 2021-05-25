package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public abstract class Handlers implements HttpHandler {
    @Override
    public abstract void handle(HttpExchange exchange) throws IOException;

    public void errorAnswer(HttpExchange httpExchange, String message){
        try {
            String jsonStr = makeJson(message);
            System.out.println(jsonStr);
            httpExchange.getResponseHeaders().set("Content-type", "application/json");
            httpExchange.sendResponseHeaders(200, jsonStr.length());

            OutputStream output = httpExchange.getResponseBody();
            output.write(jsonStr.getBytes(StandardCharsets.UTF_8));
            output.flush();
            output.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static String makeJson(String message){
        StringBuilder sb = new StringBuilder();
        sb.append("{\"error\":\"");
        sb.append(message);
        sb.append("\"}");
        System.out.println(sb.toString());
        return sb.toString();
    }

    public void out(HttpExchange exchange, String result){
        try {
            exchange.getResponseHeaders().set("Content-type", "application/json");
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