package controller;

import com.sun.net.httpserver.HttpServer;
import org.h2.jdbcx.JdbcConnectionPool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *  Класс, отвечающий за создание и запуск сервера
 */
public class BankServer {
    private static HttpServer server;
    private static JdbcConnectionPool connectionPool;

    BankServer(){ }

    /**
     * Процедура для старта и запуска сервера
     * Также создаем пул соединенй с базой данных
     */
    public static void startServer(){
        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 10);
            server.createContext("/", new OperationHandler());
            server.createContext("/client", new ClientHandler());
            server.start();

            connectionPool = DBConnect.getConnectionPool();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Функция получения соединения с базой данных
     * @return возвращает соединение из пула соединенй
     */
    public static Connection getConnection(){
        try {
            return connectionPool.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}