package school.sorokin.bank.service;

import org.springframework.stereotype.Component;
import school.sorokin.bank.model.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserService {
    private final Map<Integer, User> users = new HashMap<>();
    private int idCounter = 1;

    public User createUser(String login) {
        int id = idCounter++;
        User user = new User(id, login);
        users.put(id, user);
        return user;
    }

    public User getUserById(int id) {
        return users.get(id);
    }

    public boolean hasUser(String login) {
        return users.values().stream().anyMatch((user) -> user.getLogin().equals(login));
    }

    public Map<Integer, User> getAllUsers() {
        return users;
    }
}
