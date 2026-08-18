package school.sorokin.bank.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String login;
    private List<Account> accountList;

    public User(int id, String login) {
        this.id = id;
        this.login = login;
        this.accountList = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public List<Account> getAccountList() { return accountList; }
    public void setAccountList(List<Account> accountList) { this.accountList = accountList; }

    public void addAccount(Account account) {
        this.accountList.add(account);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", login='" + login + "', accounts=" + accountList + "}";
    }
}
