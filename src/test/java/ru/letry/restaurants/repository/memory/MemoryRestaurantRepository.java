package ru.letry.restaurants.repository.memory;

import org.springframework.stereotype.Repository;
import ru.letry.restaurants.model.Restaurant;
import ru.letry.restaurants.repository.RestaurantRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MemoryRestaurantRepository extends MemoryBaseRepository<Restaurant> implements RestaurantRepository {
    //todo need refactoring of all methods

    public List<Restaurant> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(Restaurant::getName))
                .collect(Collectors.toList());
    }

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
