package ru.letry.restaurants.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.letry.restaurants.model.Restaurant;
import ru.letry.restaurants.repository.RestaurantRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaRestaurantRepository implements RestaurantRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Restaurant save(Restaurant restaurant) {
        if (restaurant.isNew()) {
            em.persist(restaurant);
            return restaurant;
        } else {
            return em.merge(restaurant);
        }
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Restaurant get(int id) {
        return em.find(Restaurant.class, id);
    }

    @Override
    public List<Restaurant> getAll() {
        return null;
    }
}
