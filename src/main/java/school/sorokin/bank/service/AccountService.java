package school.sorokin.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import school.sorokin.bank.console.AccountCloseResult;
import school.sorokin.bank.exception.AccountNotFoundException;
import school.sorokin.bank.exception.InsufficientFundsException;
import school.sorokin.bank.model.Account;
import school.sorokin.bank.model.User;
import school.sorokin.bank.properties.AccountProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AccountService {
    private final UserService userService;
    private final AccountProperties accountProperties;
    private final Map<Integer, Account> accounts = new HashMap<>();
    private int idCounter = 1;

    @Autowired
    public AccountService(AccountProperties accountProperties, UserService userService) {
        this.accountProperties = accountProperties;
        this.userService = userService;
    }

    public Account createAccount(int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Error: User with ID " + userId + " not found.");
        }

        int id = idCounter++;
        Account newAccount = new Account(id, userId, accountProperties.getDefaultAmount());
        accounts.put(id, newAccount);
        user.getAccountList().add(newAccount);

        return newAccount;
    }

    public Account deposit(int accountId, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Error: Deposit amount must be positive.");
        }

        Account account = getAccountById(accountId)  .orElseThrow(() -> new AccountNotFoundException(
                "Error: Account with ID " + accountId + " not found."));
        account.setMoneyAmount(account.getMoneyAmount() + amount);
        return account;
    }

    public Account withdraw(int accountId, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Error: Withdraw amount must be positive.");
        }

        Account account = getAccountById(accountId)  .orElseThrow(() -> new AccountNotFoundException(
                "Error: Account with ID " + accountId + " not found."));

        var currentAmount = account.getMoneyAmount();
        var result = currentAmount - amount;

        if (result < 0) {
            throw new InsufficientFundsException(
                    "Error: insufficient funds on account id=" + accountId +
                            ", moneyAmount=" + currentAmount +
                            ", attempted withdraw=" + amount
            );
        }

        account.setMoneyAmount(result);
        return account;
    }

    public Account transfer(int fromAccountId, int toAccountId, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Error: Transfer amount must be positive.");
        }

        Account accountFrom = getAccountById(fromAccountId)
                .orElseThrow(() -> new AccountNotFoundException(
                        "Error: source account id=" + fromAccountId + " not found."));

        Account accountTo = getAccountById(toAccountId)
                .orElseThrow(() -> new AccountNotFoundException(
                        "Error: target account id=" + toAccountId + " not found."));

        if (amount > accountFrom.getMoneyAmount()) {
            throw new InsufficientFundsException(
                    "Error: insufficient funds on account id=" + accountFrom.getId() +
                            ", moneyAmount=" + accountFrom.getMoneyAmount() +
                            ", attempted transfer=" + amount
            );
        }

        accountFrom.setMoneyAmount(accountFrom.getMoneyAmount() - amount);

        int amountToReceive = (accountFrom.getUserId() != accountTo.getUserId())
                ? (int) Math.round(amount * (1 - accountProperties.getTransferCommission()))
                : amount;

        accountTo.setMoneyAmount(accountTo.getMoneyAmount() + amountToReceive);

        return accountFrom;
    }

    public AccountCloseResult closeAccount(int accountId) {
        Account accountToClose = getAccountById(accountId).orElseThrow(() -> new AccountNotFoundException(
                "Error: Account with ID " + accountId + " not found."));

        User owner = userService.getUserById(accountToClose.getUserId());
        int balanceToTransfer = accountToClose.getMoneyAmount();
        Integer targetAccountId = null;

        if (owner.getAccountList().size() <= 1) {
            throw new IllegalArgumentException(
                "Error: cannot close the only account. User must have at least one active account."
            );
        }

        Account targetAccount = owner.getAccountList().stream()
                .filter(acc -> acc.getId() != accountId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Error: No alternative account found to transfer funds."));

        targetAccount.setMoneyAmount(targetAccount.getMoneyAmount() + balanceToTransfer);
        targetAccountId = targetAccount.getId();

        accounts.remove(accountId);
        owner.getAccountList().remove(accountToClose);

        return new AccountCloseResult(accountId, balanceToTransfer, targetAccountId);
    }

    public Optional<Account> getAccountById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }
}
