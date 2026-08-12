package school.sorokin.bank.console;

public interface OperationCommand {
    void execute();
    ConsoleOperationType getOperationType();
}