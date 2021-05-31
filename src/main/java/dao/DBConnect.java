package dao;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.RunScript;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * класс соединения с базой данных
 */
public class DBConnect {
    private final static String URL = "jdbc:h2:mem:db;DB_CLOSE_DELAY=-1";
    private static String USER = "sa";
    private static String PASSWORD = "";
    private static JdbcConnectionPool connectionPool = null;

    private DBConnect(){}

    /**
     * Функция создания пула соединений с базой данных
     * @return возвращает пул соединений с базой данных
     */
    public static JdbcConnectionPool getConnectionPool(){
        if (connectionPool == null) {
            connectionPool = JdbcConnectionPool.create(URL, USER, PASSWORD);
            initDB();
        }
        return connectionPool;
    }

    public static Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    /**
     * процедура созания и заполнения таблиц базы данных
     */
    private static void initDB(){
        try {
            Connection conn = connectionPool.getConnection();
            //запускаем скрипт создания таблиц
            String schemaResourceName = "script.ddl";
            FileReader fileReader = new FileReader(schemaResourceName);
            RunScript.execute(conn, fileReader);
            //запускаем скрипт заполения таблиц
            String queryResourceFile = "dmlScript.dml";
            FileReader fileReader1 = new FileReader(queryResourceFile);
            RunScript.execute(conn, fileReader1);

        }catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * процедура закрытия пула соединений
     * @param pool пул соединений
     */
    public static void closeConnection(JdbcConnectionPool pool){
        pool.dispose();
    }
}