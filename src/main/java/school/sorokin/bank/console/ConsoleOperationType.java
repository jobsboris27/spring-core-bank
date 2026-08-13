package school.sorokin.bank.console;

public enum ConsoleOperationType {
    USER_CREATE,
    SHOW_ALL_USERS,
    ACCOUNT_CREATE,
    ACCOUNT_DEPOSIT,
    ACCOUNT_WITHDRAW,
    ACCOUNT_TRANSFER,
    ACCOUNT_CLOSE,
    EXIT,
    UNKNOWN;

    public static ConsoleOperationType fromString(String input) {
        try {
            return ConsoleOperationType.valueOf(input.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}