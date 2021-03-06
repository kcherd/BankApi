package dao;

import model.Card;
import model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * класс реализующий действия клиента по своим счетам с базой данных
 */
public class ClientsDao {
    private Client client;
    private AccDao accDao = new AccDao();
    private String noUser = "No user with such data";
    private String noAccess = "No access to account";

    private static final String CHECK_CLIENT = "select * from clients where passport_id = ?";
    private static final String GET_CARDS = "select cards.num cn, bank_account.num an, bank_account.balance " +
            "from clients join bank_account on clients.id = bank_account.id_user " +
            "join cards on bank_account.id = cards.acc_id where clients.id = ?";
    private static final String GET_ID_ACC_BY_NUM = "select id from bank_account where num = ? and id_user = ?";

    public ClientsDao(){
    }

    /**
     * Функсия проверки клиента, зарегистрирован ли он в базе или нет
     * @param passport серия и номер пасспорта
     * @return идентификатор клиента или -1, если такой клиент не зарегистрирован
     */
    private long checkClient(String passport) throws Exception {
        long id = -1;

        try(Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(CHECK_CLIENT)){
            preparedStatement.setString(1, passport);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    client = new Client();
                    id = resultSet.getLong("id");
                    client.setId(id);
                    client.setFio(resultSet.getString("fio"));
                    client.setPassport(passport);
                }
            }
        }

        return id;
    }

    /**
     * функция возвращающая список всех карт клиента
     * @param passport серия и номер пасспорта
     * @return список всех карт клиента
     * @throws Exception
     */
    public List<Card> getCards(String passport) throws Exception {
        long id = checkClient(passport);
        if(id < 0){
            throw new Exception(noUser);
        }

        List<Card> cards = new ArrayList<>();

        try(Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(GET_CARDS)){
            preparedStatement.setLong(1, client.getId());
            try(ResultSet resultSet = preparedStatement.executeQuery();){
                while (resultSet.next()){
                    Card card = new Card();
                    card.setNum(resultSet.getLong("cn"));
                    card.setAccNum(resultSet.getString("an"));
                    card.setBalance(resultSet.getDouble("balance"));
                    cards.add(card);
                }
            }
        }

        if(cards.size() > 0) {
            return cards;
        }else{
            throw new Exception("The user does not have any cards");
        }
    }

    /**
     * Фукция проверки баланса на счету
     * @param passport серия и номер пасспорта
     * @param accNum номер счета
     * @return возвращает баланс
     * @throws Exception
     */
    public double checkBalance(String passport, String accNum) throws Exception {
        if(checkClient(passport) < 0){
            throw new Exception(noUser);
        }

        if(getIdAccByNum(accNum) > 0){
            return accDao.checkBalance(accNum);
        }else{
            throw new Exception(noAccess);
        }
    }

    /**
     * функция пополнения счета
     * @param passport серия и номер пасспорта
     * @param amount сумма пополнения
     * @param accNum номер счета
     * @return true - если операция удалась, false в противном случае
     * @throws Exception
     */
    public boolean depositOfFunds(String passport, double amount, String accNum) throws Exception {
        if(checkClient(passport) < 0){
            throw new Exception(noUser);
        }

        if(getIdAccByNum(accNum) > 0){
            return accDao.depositOfFunds(amount, accNum);
        }else{
            throw new Exception(noAccess);
        }
    }

    /**
     * функция создания новой карты по номеру счета
     * @param passport серия и номер пасспорта
     * @param accNum номер счета
     * @return true - если операция удалась, false в противном случае
     * @throws Exception
     */
    public boolean newCard(String passport, String accNum) throws Exception {
        if(checkClient(passport) < 0){
            throw new Exception(noUser);
        }

        if(getIdAccByNum(accNum) > 0){
            return accDao.newCard(accNum);
        }else{
            throw new Exception(noAccess);
        }
    }

    /**
     * Функция проверки принадлежности номера счета клиенту с
     * @param accNum номер счета
     * @return идентификатор клиена или -1
     */
    private long getIdAccByNum(String accNum) throws SQLException {
        long id = -1;

        try(Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(GET_ID_ACC_BY_NUM)){
            preparedStatement.setString(1, accNum);
            preparedStatement.setLong(2, client.getId());
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    id = resultSet.getLong("id");
                }
            }
        }

        return id;
    }
}