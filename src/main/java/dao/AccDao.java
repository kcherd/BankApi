package dao;

import java.sql.*;
import java.util.Random;

/**
 * класс реализующий дейтсвия по счету с базой данных
 */
public class AccDao {
    private final Connection connection;

    /**
     * Конструктор - получает соединение с базой данных
     * @param connection соединение с базой данных
     */
    public AccDao(Connection connection){
        this.connection = connection;
    }

    /**
     * Функция проверки баланса на счете
     * @param accNum номер счета
     * @return баланс
     */
    public double checkBalance(String accNum) throws SQLException {
        double balance = 0;
        PreparedStatement statement = connection.prepareStatement("select balance from bank_account where num = ?");
        statement.setString(1,accNum);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            balance = resultSet.getDouble("balance");
        }
        resultSet.close();
        statement.close();

        return balance;
    }

    /**
     * Функция внесения средств на счет
     * @param amount сумма денег для внесения на счет
     * @param accNum номер счета
     * @return true - если операция удалась, false в противном случае
     */
    public boolean depositOfFunds(double amount, String accNum) throws SQLException {
        if(amount < 0){
            return false;
        }
        double balance = checkBalance(accNum);
        double newBalance = balance + amount;
        int result = 0;

        PreparedStatement preparedStatement = connection.prepareStatement("update bank_account set balance = ? where num = ?");
        preparedStatement.setDouble(1, newBalance);
        preparedStatement.setString(2, accNum);
        result = preparedStatement.executeUpdate();

        preparedStatement.close();

        return result == 1;
    }

    /**
     * функция создания новой карты по номеру счета
     * @param accNum номер счета
     * @return true - если операция удалась, false в противном случае
     */
    public boolean newCard(String accNum) throws SQLException {

        //проверем номер счета
        long accId = findAccByNum(accNum);
        if (accId < 0){
            return false;
        }

        long min = 1000000000000000L;
        long max = 9000000000000000L;
        Random r = new Random();
        boolean free = false;

        // генерируем номер карты и проверяем, чтобы номер не совпал с уже сущетсвующим
        long number = 0;
        while (!free) {
            number = min + ((long) (r.nextDouble() * (max - min)));
            if(findCardByNum(number) < 0){
                free = true;
            }
        }

        PreparedStatement preparedStatement = connection.prepareStatement("insert into cards (num, acc_id) values (?, ?)");
        preparedStatement.setLong(1,number);
        preparedStatement.setLong(2, accId);
        preparedStatement.executeUpdate();
        preparedStatement.close();

        return true;
    }

    /**
     * Функция поиска счета по номеру
     * @param accNum номпер счета
     * @return возвращает идентификатор счета или -1, если счета не сущетсвует
     */
    public long findAccByNum(String accNum) throws SQLException {
        long result = -1;

        PreparedStatement preparedStatement = connection.prepareStatement("select id from bank_account where num = ?");
        preparedStatement.setString(1, accNum);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            result = resultSet.getInt("id");
        }
        resultSet.close();
        preparedStatement.close();

        return result;
    }

    /**
     * функция поиска карты по номеру
     * @param cardNum номер карты
     * @return возвращает идентификатор карты или -1, если такого номера карты не сущетсвует
     */
    public long findCardByNum(long cardNum) throws SQLException {
        long result = -1;
        PreparedStatement preparedStatement = connection.prepareStatement("select id from cards where num = ?");
        preparedStatement.setLong(1, cardNum);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            result = resultSet.getInt("id");
        }
        resultSet.close();
        preparedStatement.close();

        return result;
    }
}