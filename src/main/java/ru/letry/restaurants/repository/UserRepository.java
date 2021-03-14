package ru.letry.restaurants.repository;

import org.springframework.stereotype.Repository;
import ru.letry.restaurants.model.User;

import java.util.List;

public interface UserRepository {
    //null if not found, when updated
    User save(User user);

    //false if not exists
    boolean delete(int id);

    //null if not found
    User get(int id);

    //null if not found
    User getByEmail(String email);

    List<User> getAll();
}
