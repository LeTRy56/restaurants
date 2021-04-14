package ru.letry.restaurants.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.letry.restaurants.model.Dish;
import ru.letry.restaurants.model.Restaurant;
import ru.letry.restaurants.model.Role;
import ru.letry.restaurants.model.User;
import ru.letry.restaurants.repository.DishRepository;
import ru.letry.restaurants.repository.RestaurantRepository;
import ru.letry.restaurants.repository.UserRepository;

import java.util.List;

import static ru.letry.restaurants.util.ValidationUtil.*;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, UserRepository userRepository, DishRepository dishRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant, int userId) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return getUser(userId).getRoles().contains(Role.ADMIN) ? restaurantRepository.save(restaurant) : null;
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepository.get(id), id);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Restaurant restaurant, int userId) {
        Assert.notNull(restaurant, "restaurant must not be null");
        if (getUser(userId).getRoles().contains(Role.ADMIN)) {
            for (Dish dish : restaurant.getDishes()) {
                checkNotFoundWithId(dishRepository.save(dish, restaurant.id()), dish.id());
            }
            checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.id());
        }
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id, int userId) {
        if (getUser(userId).getRoles().contains(Role.ADMIN)) {
            checkNotFoundWithId(restaurantRepository.delete(id), id);
        }
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    private User getUser(int userId) {
        return checkNotFoundWithId(userRepository.get(userId), userId);
    }

}
