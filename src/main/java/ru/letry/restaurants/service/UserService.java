package ru.letry.restaurants.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.letry.restaurants.model.User;
import ru.letry.restaurants.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    public User get(int id) {
        //todo validationUtil
        return repository.get(id);
    }

    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        //todo validationUtil
        repository.save(user);
    }

    public void delete(int id) {
        //todo validationUtil
        repository.delete(id);
    }


    public User getByEmail(String email) {
        Assert.notNull(email, "email must be null");
        //todo validationUtil
        return repository.getByEmail(email);
    }

    public List<User> getAll() {
        return repository.getAll();
    }
}
