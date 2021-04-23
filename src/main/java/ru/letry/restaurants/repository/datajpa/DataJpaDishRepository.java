package ru.letry.restaurants.repository.datajpa;

import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.letry.restaurants.model.Dish;
import ru.letry.restaurants.model.Restaurant;
import ru.letry.restaurants.repository.DishRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        clearHibernateCache();
        return saved;
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        clearHibernateCache();
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

    private void clearHibernateCache() {
        //invalidate Hibernate 2nd level cache:
        Session session = em.unwrap(Session.class);
        SessionFactory sessionFactory = session.getSessionFactory();
        Cache cache = sessionFactory.getCache();
        cache.evictAllRegions();
//        cache.evictEntityData(Restaurant.class, restaurantId);
    }
}
