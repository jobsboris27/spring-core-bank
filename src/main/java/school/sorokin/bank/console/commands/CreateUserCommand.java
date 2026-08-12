package school.sorokin.bank.console.commands;

import org.springframework.stereotype.Component;
import school.sorokin.bank.console.ConsoleOperationType;
import school.sorokin.bank.console.OperationCommand;

@Component
public class CreateUserCommand implements OperationCommand {
    @Override
    public void execute() {
        // Логика выполнения команды
    }
    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }
}
