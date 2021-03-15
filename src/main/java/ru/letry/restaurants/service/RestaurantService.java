package ru.letry.restaurants.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.letry.restaurants.model.Restaurant;
import ru.letry.restaurants.repository.RestaurantRepository;

import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant create(Restaurant restaurant, int userId) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant, userId);
    }

    public Restaurant get(int id, int userId) {
        //todo validationUtil
        return repository.get(id, userId);
    }

    public void update(Restaurant restaurant, int userId) {
        Assert.notNull(restaurant, "restaurant must not be null");
        //todo validationUtil
        repository.save(restaurant, userId);
    }

    public void delete(int id, int userId) {
        //todo validationUtil
        repository.delete(id, userId);
    }

    public List<Restaurant> getAll(int userId) {
        return repository.getAll(userId);
    }

}
