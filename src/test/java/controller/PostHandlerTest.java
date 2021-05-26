package controller;

import dao.DBConnect;
import model.InData;
import model.Passport;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class PostHandlerTest {
    PostHandler postHandler;

    @Before
    public void Init() throws SQLException {
        JdbcConnectionPool connectionPool = DBConnect.getConnectionPool();
        postHandler = new PostHandler(connectionPool.getConnection());
    }

    @Test
    public void handle() {
    }

    @Test
    public void parseInputStream(){

    }

    @Test
    public void showHTML(){

    }
}