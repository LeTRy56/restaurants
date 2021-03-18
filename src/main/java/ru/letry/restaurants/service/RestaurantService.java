package ru.letry.restaurants.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.letry.restaurants.model.Restaurant;
import ru.letry.restaurants.model.Role;
import ru.letry.restaurants.model.User;
import ru.letry.restaurants.repository.RestaurantRepository;
import ru.letry.restaurants.repository.UserRepository;

import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository repository;
    private final UserRepository userRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.repository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public Restaurant create(Restaurant restaurant, int userId) {
        Assert.notNull(restaurant, "restaurant must not be null");
        //todo validationUtil
        //todo: throw exception?
        return getUser(userId).getRoles().contains(Role.ADMIN) ? repository.save(restaurant) : null;
    }

    public Restaurant get(int id) {
        //todo validationUtil
        return repository.get(id);
    }

    public void update(Restaurant restaurant, int userId) {
        Assert.notNull(restaurant, "restaurant must not be null");
        //todo validationUtil
        if (getUser(userId).getRoles().contains(Role.ADMIN)) {
            repository.save(restaurant);
        }
    }

    public void delete(int id, int userId) {
        //todo validationUtil
        repository.delete(id);
    }

    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    private User getUser(int userId) {
        return userRepository.get(userId);
    }

}
