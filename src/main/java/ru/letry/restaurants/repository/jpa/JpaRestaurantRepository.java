package ru.letry.restaurants.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.letry.restaurants.model.Restaurant;
import ru.letry.restaurants.repository.RestaurantRepository;

import java.util.List;

@Repository
public class JpaRestaurantRepository implements RestaurantRepository {
    @Override
    public Restaurant save(Restaurant restaurant, int userId) {
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        return false;
    }

    @Override
    public Restaurant get(int id, int userId) {
        return null;
    }

    @Override
    public List<Restaurant> getAll(int userId) {
        return null;
    }
}
