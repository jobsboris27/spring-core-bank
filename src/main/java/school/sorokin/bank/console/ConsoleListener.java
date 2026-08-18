package school.sorokin.bank.console;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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
        String availableCommands = Arrays.stream(ConsoleOperationType.values())
                .filter(op -> op != ConsoleOperationType.UNKNOWN) // убираем UNKNOWN
                .map(Enum::name)                                  // преобразуем в строки
                .collect(Collectors.joining(", "));

        System.out.println("MiniBank started.");
        System.out.println("Type EXIT to stop.");
        System.out.printf("Available commands: %s%n", availableCommands);

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

