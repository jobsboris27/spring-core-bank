package school.sorokin.bank.console.commands;

import org.springframework.stereotype.Component;
import school.sorokin.bank.console.CommandContext;
import school.sorokin.bank.console.CommandParam;
import school.sorokin.bank.console.ConsoleOperationType;
import school.sorokin.bank.console.OperationCommand;
import school.sorokin.bank.exception.AccountNotFoundException;
import school.sorokin.bank.service.AccountService;

import java.util.List;

@Component
public class AccountDepositCommand implements OperationCommand {
    private final AccountService accountService;

    public AccountDepositCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String execute(CommandContext context) {
        try {
            var accountId = context.getInt("accountId");
            var amount = context.getInt("amount");
            var account = accountService.deposit(accountId, amount);
            return "Deposited " + amount + " to account " + accountId + ". New balance: " + account.getMoneyAmount();
        } catch (NumberFormatException e) {
            return "Error: Invalid number format. Please enter digits only.";
        } catch (AccountNotFoundException | IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }

    @Override
    public List<CommandParam> getRequiredParams() {
        return List.of(
            new CommandParam("accountId", "Enter account id: "),
            new CommandParam("amount", "Enter amount: ")
        );
    }
}
