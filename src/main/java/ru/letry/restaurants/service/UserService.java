package ru.letry.restaurants.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.letry.restaurants.model.User;
import ru.letry.restaurants.repository.UserRepository;

import java.util.List;

import static ru.letry.restaurants.util.ValidationUtil.checkNotFound;
import static ru.letry.restaurants.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.save(user), user.id());
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }


    public User getByEmail(String email) {
        Assert.notNull(email, "email must be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Cacheable("users")
    public List<User> getAll() {
        return repository.getAll();
    }
}
