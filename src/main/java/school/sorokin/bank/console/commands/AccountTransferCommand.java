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
public class AccountTransferCommand implements OperationCommand {
    private final AccountService accountService;

    public AccountTransferCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String execute(CommandContext context) {
        try {
            var fromAccountId = context.getInt("fromAccountId");
            var toAccountId = context.getInt("toAccountId");
            var amount = context.getInt("amount");
            var account = accountService.transfer(fromAccountId, toAccountId, amount);

            return String.format("Transfer completed from account %s to account %s. New balance: %s", fromAccountId, toAccountId, account.getMoneyAmount());
        } catch (NumberFormatException e) {
            return "Error: Invalid number format. Please enter digits only.";
        } catch (AccountNotFoundException | InsufficientFundsException | IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }

    @Override
    public List<CommandParam> getRequiredParams() {
        return List.of(
            new CommandParam("fromAccountId", "Enter source account id: "),
                new CommandParam("toAccountId", "Enter target account id: "),
            new CommandParam("amount", "Enter amount: ")
        );
    }
}
