package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    private long generatorId = 1L;

    public long generateId() {
        return generatorId++;
    }

    @Override
    public User addUser(User user) {
        user.setId(generateId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void updateUser(User modifiedUser) {
        if (users.containsKey(modifiedUser.getId()))
            users.put(modifiedUser.getId(), modifiedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        users.remove(userId);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public void addFriend(long userId, long friendId) {
        users.get(userId).getFriendsIds().add(friendId);
        users.get(friendId).getFriendsIds().add(userId);
    }

    @Override
    public void deleteFriend(long userId, long friendId) {
        users.get(userId).getFriendsIds().remove(friendId);
        users.get(friendId).getFriendsIds().remove(userId);
    }

    @Override
    public Collection<User> getUserFriends(long userId) {
        return users.get(userId).getFriendsIds()
                .stream()
                .map(users::get)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<User> getCommonFriends(long userId, long otherId) {
        return users.get(userId).getFriendsIds()
                .stream()
                .filter(users.get(otherId).getFriendsIds()::contains)
                .map(users::get)
                .collect(Collectors.toList());
    }
}
