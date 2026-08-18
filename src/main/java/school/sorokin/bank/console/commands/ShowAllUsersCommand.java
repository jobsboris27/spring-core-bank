package school.sorokin.bank.console.commands;

import org.springframework.stereotype.Component;
import school.sorokin.bank.console.CommandContext;
import school.sorokin.bank.console.CommandParam;
import school.sorokin.bank.console.ConsoleOperationType;
import school.sorokin.bank.console.OperationCommand;
import school.sorokin.bank.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShowAllUsersCommand implements OperationCommand {
    private final UserService userService;

    public ShowAllUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(CommandContext context) {
        var usersMap = userService.getAllUsers();

        if (usersMap.isEmpty()) {
            return "No users found.";
        }

        return usersMap.values().stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }

    @Override
    public List<CommandParam> getRequiredParams() {
        return List.of();
    }
}
