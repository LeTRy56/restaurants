package ru.letry.restaurants.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.letry.restaurants.model.User;
import ru.letry.restaurants.repository.UserRepository;

import java.util.List;

@Repository
public class JpaUserRepository implements UserRepository {
    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
