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

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    public Restaurant get(int id) {
        //todo validationUtil
        return repository.get(id);
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        //todo validationUtil
        repository.save(restaurant);
    }

    public void delete(int id) {
        //todo validationUtil
        repository.delete(id);
    }

    public List<Restaurant> getAll() {
        return repository.getAll();
    }

}
