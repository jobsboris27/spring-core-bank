package school.sorokin.bank.console.commands;

import org.springframework.stereotype.Component;
import school.sorokin.bank.console.CommandContext;
import school.sorokin.bank.console.CommandParam;
import school.sorokin.bank.console.ConsoleOperationType;
import school.sorokin.bank.console.OperationCommand;
import school.sorokin.bank.service.UserService;

import java.util.List;

@Component
public class CreateUserCommand implements OperationCommand {
    private final UserService userService;

    public CreateUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(CommandContext context) {
        var login = context.getString("login");

        if (userService.hasUser(login)) {
            return String.format("User '%s' already exists", login);
        }

        var user = userService.createUser(login);

        return String.format("User created: %s", user);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }

    @Override
    public List<CommandParam> getRequiredParams() {
        return List.of(
            new CommandParam("login", "Enter login: ")
        );
    }
}
