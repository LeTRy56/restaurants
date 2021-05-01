package ru.letry.restaurants.util;

import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;

public class CacheUtil {
    public static void clearHibernateCache(EntityManager entityManager) {
        //invalidate Hibernate 2nd level cache:
        Session session = entityManager.unwrap(Session.class);
        SessionFactory sessionFactory = session.getSessionFactory();
        Cache cache = sessionFactory.getCache();
        cache.evictAllRegions();
//        cache.evictEntityData(Restaurant.class, restaurantId);
    }
}
