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
import ru.letry.restaurants.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.letry.restaurants.util.ValidationUtil.*;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserService userService;
    private final DishService dishService;

    public RestaurantService(RestaurantRepository restaurantRepository, UserService userService, DishService dishService) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
        this.dishService = dishService;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant, int userId) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNew(restaurant);
        if (getUser(userId).getRoles().contains(Role.ADMIN)) {
            Restaurant saved = restaurantRepository.save(restaurant);
            Set<Dish> dishes = restaurant.getDishes();
            if (dishes != null) {
                for (Dish dish : dishes) {
                    checkNew(dish);
                    dishService.create(dish, saved.getId(), userId);
                }
            }
            return get(saved.getId(), LocalDate.now());
        } else {
            return null;
        }
    }

    public Restaurant get(int id, LocalDate date) {
        Assert.notNull(date, "date must not be null");
        Restaurant restaurant = restaurantRepository.get(id);
        if (restaurant != null) {
            restaurant.setDishes(dishService.getAll(restaurant.id(), date));
        }
        return checkNotFoundWithId(restaurant, id);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Restaurant restaurant, int userId) {
        Assert.notNull(restaurant, "restaurant must not be null");
        if (getUser(userId).getRoles().contains(Role.ADMIN)) {
            for (Dish dish : restaurant.getDishes()) {
                dishService.update(dish, restaurant.id(), userId);
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
        List<Restaurant> enabledRestaurants = restaurantRepository.getAll().stream()
                .filter(Restaurant::isEnabled)
                .collect(Collectors.toList());

        enabledRestaurants.forEach(
                restaurant -> restaurant.setDishes(dishService.getAll(restaurant.id(), LocalDate.now())));
        return enabledRestaurants;
    }

    private User getUser(int userId) {
        return checkNotFoundWithId(userService.get(userId), userId);
    }

}
