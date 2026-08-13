package school.sorokin.bank.console;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class ConsoleListener {
    private final Map<ConsoleOperationType, OperationCommand> commandMap;

    public ConsoleListener(List<OperationCommand> commands) {
        this.commandMap = new HashMap<>();
        commands.forEach(command ->
                commandMap.put(command.getOperationType(), command)
        );
    }

    public void start() {
        new Thread(this::listenConsoleInput).start();
    }

    private void listenConsoleInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("MiniBank started.");
        System.out.println("Type EXIT to stop.");
        System.out.println("Available commands: USER_CREATE, SHOW_ALL_USERS, ACCOUNT_CREATE,\n" +
                "ACCOUNT_DEPOSIT, ACCOUNT_WITHDRAW, ACCOUNT_TRANSFER, ACCOUNT_CLOSE, EXIT");

        while(true) {
            System.out.print("\nEnter command: ");
            String input = scanner.next();
            scanner.nextLine();
            ConsoleOperationType commandType = ConsoleOperationType.fromString(input);
            OperationCommand command = commandMap.get(commandType);

            if (commandType == ConsoleOperationType.EXIT) {
                System.out.println("MiniBank stopped.");
                System.exit(0);
            }

            if (command != null) {
                Map<String, String> params = new HashMap<>();

                for(CommandParam param : command.getRequiredParams()) {
                    System.out.print(param.prompt());
                    var userInput = scanner.nextLine().trim();
                    params.put(param.key(), userInput);
                }

                var context = new CommandContext(params);
                String response = command.execute(context);

                if (response != null && !response.isEmpty()) {
                    System.out.println(response);
                }
            } else {
                System.out.println("Error: Unknown command. Please try again.");
            }
        }
    }
}

