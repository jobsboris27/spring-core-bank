package school.sorokin.bank.console;

import java.util.List;

public interface OperationCommand {
    String execute(CommandContext context);
    ConsoleOperationType getOperationType();
    List<CommandParam> getRequiredParams();
}