package school.sorokin.bank.console.commands;

import org.springframework.stereotype.Component;
import school.sorokin.bank.console.CommandContext;
import school.sorokin.bank.console.CommandParam;
import school.sorokin.bank.console.ConsoleOperationType;
import school.sorokin.bank.console.OperationCommand;
import school.sorokin.bank.exception.AccountNotFoundException;
import school.sorokin.bank.exception.InsufficientFundsException;
import school.sorokin.bank.service.AccountService;

import java.util.List;

@Component
public class AccountWithdrawCommand implements OperationCommand {
    private final AccountService accountService;

    public AccountWithdrawCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String execute(CommandContext context) {
        try {
            var accountId = context.getInt("accountId");
            var amount = context.getInt("amount");
            var account = accountService.withdraw(accountId, amount);
            return "Withdrew " + amount + " from account " + accountId + ". New balance: " + account.getMoneyAmount();
        } catch (NumberFormatException e) {
            return "Error: Invalid number format. Please enter digits only.";
        } catch (AccountNotFoundException | InsufficientFundsException | IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_WITHDRAW;
    }

    @Override
    public List<CommandParam> getRequiredParams() {
        return List.of(
            new CommandParam("accountId", "Enter account id: "),
            new CommandParam("amount", "Enter amount: ")
        );
    }
}
