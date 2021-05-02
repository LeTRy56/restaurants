package ru.letry.restaurants.repository.datajpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.letry.restaurants.model.Dish;
import ru.letry.restaurants.repository.DishRepository;
import ru.letry.restaurants.util.CacheUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaDishRepository implements DishRepository {
    private final CrudDishRepository crudDishRepository;
    private final CrudRestaurantRepository restaurantRepository;

    @PersistenceContext
    private EntityManager em;

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
        Dish saved = crudDishRepository.save(dish);
        CacheUtil.clearHibernateCache(em);
        return saved;
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        boolean isDeleted = crudDishRepository.delete(id, restaurantId) != 0;
        CacheUtil.clearHibernateCache(em);
        return isDeleted;
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

    @Override
    public List<Dish> getAll(int restaurantId, LocalDate date) {
        return crudDishRepository.getAll(restaurantId, date);
    }
}
