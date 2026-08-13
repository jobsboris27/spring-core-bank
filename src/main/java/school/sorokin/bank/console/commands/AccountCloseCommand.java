package school.sorokin.bank.console.commands;

import org.springframework.stereotype.Component;
import school.sorokin.bank.console.*;
import school.sorokin.bank.exception.AccountNotFoundException;
import school.sorokin.bank.exception.InsufficientFundsException;
import school.sorokin.bank.service.AccountService;

import java.util.List;

@Component
public class AccountCloseCommand implements OperationCommand {
    private final AccountService accountService;

    public AccountCloseCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String execute(CommandContext context) {
        try {
            var accountId = context.getInt("accountId");
            AccountCloseResult result = accountService.closeAccount(accountId);
            String transferInfo = result.targetAccountId() != null
                    ? "Remaining balance " + result.remainingBalance() + " transferred to account " + result.targetAccountId() + "."
                    : "No other accounts to transfer remaining balance.";

            return "Account " + result.closedAccountId() + " closed. " + transferInfo;
        } catch (NumberFormatException e) {
            return "Error: Invalid number format. Please enter digits only.";
        } catch (AccountNotFoundException | IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }

    @Override
    public List<CommandParam> getRequiredParams() {
        return List.of(
            new CommandParam("accountId", "Enter account id to close: ")
        );
    }
}
