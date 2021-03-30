package ru.letry.restaurants.repository.datajpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.letry.restaurants.model.Dish;
import ru.letry.restaurants.repository.DishRepository;

import java.util.List;

@Repository
public class DataJpaDishRepository implements DishRepository {
    private final CrudDishRepository crudDishRepository;
    private final CrudRestaurantRepository restaurantRepository;

    public DataJpaDishRepository(CrudDishRepository crudDishRepository, CrudRestaurantRepository restaurantRepository) {
        this.crudDishRepository = crudDishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        if (!dish.isNew() && get(dish.getId(), restaurantId) == null) {
            return null;
        }
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        return crudDishRepository.save(dish);
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        return crudDishRepository.delete(id, restaurantId) != 0;
    }

    @Override
    public Dish get(int id, int restaurantId) {
        return crudDishRepository.findById(id)
                .filter(dish -> dish.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }

    @Override
    public List<Dish> getAll(int restaurantId) {
        return crudDishRepository.getAll(restaurantId);
    }
}
