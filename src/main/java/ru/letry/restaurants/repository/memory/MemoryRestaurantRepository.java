package ru.letry.restaurants.repository.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.letry.restaurants.model.Dish;
import ru.letry.restaurants.model.Restaurant;
import ru.letry.restaurants.repository.RestaurantRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryRestaurantRepository implements RestaurantRepository {
    //todo need refactoring of all methods and field map
    private static final Logger LOG = LoggerFactory.getLogger(MemoryRestaurantRepository.class);

    private final Map<Integer, MemoryBaseRepository<Restaurant>> usersRestaurantsMap = new ConcurrentHashMap<>();

    {
        var userRestaurants = new MemoryBaseRepository<Restaurant>();
        userRestaurants.put(new Restaurant(100, "First restaurant", Set.of(new Dish(10, "Dish 1", 45d), new Dish(11, "Dish 2", 55d))));
        userRestaurants.put(new Restaurant(101, "Second restaurant", Set.of(new Dish(12, "Dish 3", 35d), new Dish(13, "Dish 4", 25d))));
        usersRestaurantsMap.put(100000, userRestaurants);
//        usersRestaurantsMap.put(100001, new MemoryBaseRepository<>());
    }

    @Override
    public Restaurant save(Restaurant restaurant, int userId) {
        return usersRestaurantsMap.get(userId).save(restaurant);
    }

    @Override
    public boolean delete(int id, int userId) {
        var restaurants = usersRestaurantsMap.get(userId);
        return restaurants != null && restaurants.delete(id);
    }

    @Override
    public Restaurant get(int id, int userId) {
        var restaurants = usersRestaurantsMap.get(userId);
        return restaurants == null ? null : restaurants.get(id);
    }

    @Override
    public List<Restaurant> getAll(int userId) {
        var restaurants = usersRestaurantsMap.get(userId);
        return restaurants == null ? Collections.emptyList() :
                restaurants.getCollection().stream()
                .sorted(Comparator.comparing(Restaurant::getName))
                .collect(Collectors.toList());
    }
}
