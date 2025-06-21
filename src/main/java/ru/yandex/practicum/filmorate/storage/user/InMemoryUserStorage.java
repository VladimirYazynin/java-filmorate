package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    private long generatorId = 1L;

    public long generateId() {
        return generatorId++;
    }

    @Override
    public User addUser(User user) {
        user.setId(generatorId);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> updateUser(User modifiedUser) {
        if (!users.containsKey(modifiedUser.getId()))
            return Optional.empty();
        users.put(modifiedUser.getId(), modifiedUser);
        return Optional.of(modifiedUser);
    }

    @Override
    public User deleteUser(Long userId) {
        return users.remove(userId);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return Optional.of(users.get(userId));
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }
}
