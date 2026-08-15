package school.sorokin.bank.console.commands;

import org.springframework.stereotype.Component;
import school.sorokin.bank.console.CommandContext;
import school.sorokin.bank.console.CommandParam;
import school.sorokin.bank.console.ConsoleOperationType;
import school.sorokin.bank.console.OperationCommand;
import school.sorokin.bank.exception.ErrorMessages;
import school.sorokin.bank.service.AccountService;
import school.sorokin.bank.service.UserService;


import java.util.List;

@Component
public class AccountCreateCommand implements OperationCommand {
    private final AccountService accountService;
    private final UserService userService;

    public AccountCreateCommand(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public String execute(CommandContext context) {
        try {
            var userId = context.getInt("userId");
            var account = accountService.createAccount(userId);

            return String.format("Account created. %s", account);
        } catch (NumberFormatException e) {
            return ErrorMessages.INVALID_NUMBER;
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }

    @Override
    public List<CommandParam> getRequiredParams() {
        return List.of(
            new CommandParam("userId", "Enter user id: ")
        );
    }
}
