package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private final Map<Long, Set<Long>> userFriendIds = new HashMap<>();

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

    @Override
    public void addFriend(long userId, long friendId) {
        userFriendIds.get(userId).add(friendId);
    }

    @Override
    public void deleteFriend(long userId, long friendId) {
        userFriendIds.get(userId).remove(friendId);
    }

    @Override
    public Collection<User> getUserFriends(long userId) {
        return userFriendIds.get(userId)
                .stream()
                .map(users::get)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<User> getCommonFriends(long userId, long otherId) {
        return userFriendIds.get(userId)
                .stream()
                .filter(userFriendIds.get(otherId)::contains)
                .map(users::get)
                .collect(Collectors.toList());
    }
}
