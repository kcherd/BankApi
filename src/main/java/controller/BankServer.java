package controller;

import com.sun.net.httpserver.HttpServer;
import dao.DBConnect;
import org.h2.jdbcx.JdbcConnectionPool;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 *  Класс, отвечающий за создание и запуск сервера
 */
public class BankServer {
    private static HttpServer server;
    private static JdbcConnectionPool connectionPool;

    /**
     *  Приватный конструктор класса, чтобы создавать только один экземпляр сервера
     */
    private BankServer(){ }

    /**
     * Процедура для старта и запуска сервера
     * Также создаем пул соединенй с базой данных
     */
    public static void startServer(){
        try {
            //connectionPool = DBConnect.getConnectionPool();

            server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 10);
            server.createContext("/", new GetHandler());
            server.createContext("/client", new PostHandler());
            server.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void createPool(){
        connectionPool = DBConnect.getConnectionPool();
    }

    public static void disposePool(){
        connectionPool.dispose();
    }

    public static void stopServer(){
        //DBConnect.closeConnection(connectionPool);
        server.stop(0);
    }
}