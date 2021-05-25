package controller;

import com.google.gson.Gson;
import dao.ClientsDao;
import model.Account;
import model.Amount;
import model.Card;
import model.Passport;

import java.util.List;

public class ApiMethods {

    private final ClientsDao clientsDao = new ClientsDao(BankServer.getConnection());
    Gson gson = new Gson();

    /**
     * Метод создания новой карты
     * @param account параметры запроса: серия и номер паспотра, номер счета
     * возвращает true, если карта создана и страницу с ошибкой, если карта не создана
     */
    public boolean newCard(Account account) throws Exception {
        return clientsDao.newCard(account.getPassport(), account.getAccount());
    }

    /**
     * Метод вывода всех карт
     * @param passport серия и номер паспорта
     * возвращает json объект со спискои карт пользователя или описание ошибки
     */
    public String allCards(Passport passport) throws Exception {
        List<Card> cards = clientsDao.getCards(passport.getPassport());
        return gson.toJson(cards);
    }

    /**
     * Метод получения баланса по номеру сечта
     * @param account параметры: номер и серия паспорта, номер счета
     * выводит баланс или информацию об ошибке
     */
    public double balance(Account account) throws Exception {
        return clientsDao.checkBalance(account.getPassport(), account.getAccount());
    }

    /**
     * Метод производящий пополнение баланса счета
     * @param amount: серия и номер паспорта, сумма, номер счета
     * выводит true или сообщение об ошибке
     */
    public boolean deposit(Amount amount) throws Exception {
        return clientsDao.depositOfFunds(amount.getPassport(), amount.getAmount(), amount.getAccount());
    }
}