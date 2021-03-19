package ru.letry.restaurants.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.letry.restaurants.model.Dish;
import ru.letry.restaurants.model.Role;
import ru.letry.restaurants.model.User;
import ru.letry.restaurants.repository.DishRepository;
import ru.letry.restaurants.repository.UserRepository;

import java.util.List;
import java.util.Objects;

import static ru.letry.restaurants.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {
    private final DishRepository dishRepository;
    private final UserRepository userRepository;

    public DishService(DishRepository dishRepository, UserRepository userRepository) {
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
    }

    public Dish create(Dish dish, int restaurantId, int userId) {
        Assert.notNull(dish, "restaurant must not be null");
        //todo: throw exception if not admin?
        return getUser(userId).getRoles().contains(Role.ADMIN) ? dishRepository.save(dish, restaurantId) : null;
    }

    public Dish get(int id, int restaurantId) {
        return checkNotFoundWithId(dishRepository.get(id, restaurantId), id);
    }

    public void update(Dish dish, int restaurantId, int userId) {
        Objects.requireNonNull(dish, "dish must not be null");
        if (getUser(userId).getRoles().contains(Role.ADMIN)) {
            checkNotFoundWithId(dishRepository.save(dish, restaurantId), dish.id());
        }
    }

    public void delete(int id, int restaurantId, int userId) {
        if (getUser(userId).getRoles().contains(Role.ADMIN)) {
            checkNotFoundWithId(dishRepository.delete(id, restaurantId), id);
        }
    }

    public List<Dish> getAll(int restaurantId) {
        return dishRepository.getAll(restaurantId);
    }

    private User getUser(int userId) {
        return checkNotFoundWithId(userRepository.get(userId), userId);
    }
}
