package school.sorokin.bank.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {
    @Value("${account.default-amount}")
    private double defaultAmount;

    @Value("${account.transfer-commission}")
    private double transferCommission;

    public double getDefaultAmount() {
        return defaultAmount;
    }

    public double getTransferCommission() {
        return transferCommission;
    }
}
