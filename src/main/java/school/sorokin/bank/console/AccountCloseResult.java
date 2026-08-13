package school.sorokin.bank.console;

public record AccountCloseResult(
        int closedAccountId,
        int remainingBalance,
        Integer targetAccountId // Integer, так как если переносить некуда (баланс 0), тут будет null
) {}
