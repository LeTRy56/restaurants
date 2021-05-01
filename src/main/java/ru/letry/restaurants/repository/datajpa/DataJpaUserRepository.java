package ru.letry.restaurants.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.letry.restaurants.model.User;
import ru.letry.restaurants.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static ru.letry.restaurants.util.CacheUtil.clearHibernateCache;

@Repository
public class DataJpaUserRepository implements UserRepository {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    private final CrudUserRepository crudRepository;

    @PersistenceContext
    private EntityManager em;

    public DataJpaUserRepository(CrudUserRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public User save(User user) {
        clearHibernateCache(em);
        return crudRepository.save(user);
    }

    @Override
    public boolean delete(int id) {
        clearHibernateCache(em);
        return crudRepository.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return crudRepository.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return crudRepository.findAll(SORT_NAME_EMAIL);
    }
}
