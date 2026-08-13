package school.sorokin.bank.console;

import java.util.Map;

public record CommandContext(Map<String, String> params) {
    public String getString(String key) {
        return params.get(key);
    }

    public int getInt(String key) {
        String value = params.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Required parameter '" + key + "' is not present");
        }
        return Integer.parseInt(value);
    }
}