package dao;

import com.google.gson.Gson;
import model.Counterparty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CounterpartyDao {
    private Connection connection;
    private Gson gson = new Gson();

    public CounterpartyDao(Connection connection){
        this.connection = connection;
    }

    public boolean addCounterparty(String jsonCounterparty) throws Exception {
        Counterparty counterparty = gson.fromJson(jsonCounterparty, Counterparty.class);

        //поиск контрагента в базе
        long id = findCounterpartyByInn(counterparty.getInn());
        //если нет, то добавление в базу нового контрагента
        if(id < 0){
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into counterparty (inn, ogrn) values (?, ?)");
            preparedStatement.setLong(1, counterparty.getInn());
            preparedStatement.setLong(2, counterparty.getOgrn());

            int result = preparedStatement.executeUpdate();
            preparedStatement.close();

            if(result != 1){
                throw new Exception("Failed to add counterparty");
            }
        }

        //поиск счетов контрагента
        for(Counterparty.CounterpartyAccount acc : counterparty.getCounterpartyAccounts()){
            long idAcc = findAccByNum(acc.getNum());
            //если счетов нет, то довабление счетов
            if(id < 0){
                addAccount(acc, id);
            }
        }
        return true;
    }

    public boolean addAccount(Counterparty.CounterpartyAccount account, long idC) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into counterparty_acc (num, balance, id_counterparty) values (?, ?, ?)"
        );
        preparedStatement.setString(1, account.getNum());
        preparedStatement.setDouble(2, account.getBalance());
        preparedStatement.setLong(3, idC);

        int result = preparedStatement.executeUpdate();
        preparedStatement.close();

        if(result == 1){
            return true;
        } else{
            throw new Exception("Filed to add account");
        }
    }

    public long findCounterpartyByInn(long inn) throws SQLException {
        long id = -1;

        PreparedStatement preparedStatement = connection.prepareStatement(
                "select id from counterparty where inn = ?");
        preparedStatement.setLong(1, inn);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            id = resultSet.getLong("id");
        }

        resultSet.close();
        preparedStatement.close();

        return id;
    }

    public long findAccByNum(String num) throws SQLException {
        long id = -1;

        PreparedStatement preparedStatement = connection.prepareStatement(
                "select id from counterparty_acc where num = ?"
        );
        preparedStatement.setString(1, num);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            id = resultSet.getLong("id");
        }

        resultSet.close();
        preparedStatement.close();

        return id;
    }
}
