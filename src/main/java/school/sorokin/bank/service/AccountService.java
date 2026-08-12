package school.sorokin.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import school.sorokin.bank.model.Account;
import school.sorokin.bank.properties.AccountProperties;

import java.util.HashMap;
import java.util.Map;

@Component
public class AccountService {
    private final AccountProperties accountProperties;
    private final Map<Integer, Account> accounts = new HashMap<>();

    @Autowired
    public AccountService(AccountProperties accountProperties) {
        this.accountProperties = accountProperties;
    }
}
