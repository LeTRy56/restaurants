package ru.letry.restaurants.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.letry.restaurants.model.Restaurant;
import ru.letry.restaurants.model.Role;
import ru.letry.restaurants.model.User;
import ru.letry.restaurants.repository.RestaurantRepository;
import ru.letry.restaurants.repository.UserRepository;

import java.util.List;

import static ru.letry.restaurants.util.ValidationUtil.*;

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
        //todo: throw exception if not admin?
        return getUser(userId).getRoles().contains(Role.ADMIN) ? repository.save(restaurant) : null;
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

//    @Transactional
    public void update(Restaurant restaurant, int userId) {
        Assert.notNull(restaurant, "restaurant must not be null");
        if (getUser(userId).getRoles().contains(Role.ADMIN)) {
            //http 500 - throws exception when create restaurant with not unique name or update because exists unique index "restaurants_unique_name_idx"
            //so need delete before save
//            delete(restaurant.id(), userId);
            checkNotFoundWithId(repository.save(restaurant), restaurant.id());
        }
    }

    public void delete(int id, int userId) {
        if (getUser(userId).getRoles().contains(Role.ADMIN)) {
            checkNotFoundWithId(repository.delete(id), id);
        }
    }

    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    private User getUser(int userId) {
        return checkNotFoundWithId(userRepository.get(userId), userId);
    }

}
