package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    private long generatorId = 0L;

    public long generateId() {
        return ++generatorId;
    }

    @Override
    public User addUser(User user) {
        user.setId(generatorId);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User modifiedUser) {
        users.put(modifiedUser.getId(), modifiedUser);
        return modifiedUser;
    }

    @Override
    public User deleteUser(Long userId) {
        return users.remove(userId);
    }

    @Override
    public User getUserById(Long userId) {
        return users.get(userId);
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }
}
